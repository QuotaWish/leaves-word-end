package com.quotawish.leaveword.job.cycle;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageType;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.client.workflows.run.model.WorkflowEventError;
import com.coze.openapi.client.workflows.run.model.WorkflowEventType;
import com.coze.openapi.service.service.CozeAPI;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.manager.CozeManager;
import com.quotawish.leaveword.model.entity.audio.MediaCreator;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.model.enums.MediaType;
import com.quotawish.leaveword.model.enums.WordStatus;
import com.quotawish.leaveword.service.EnglishWordService;
import com.quotawish.leaveword.service.MediaCreatorService;
import com.quotawish.leaveword.service.WordStatusChangeService;
import com.quotawish.leaveword.utils.task.WordProcessor;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.xmlbeans.impl.store.Public2.save;

@Slf4j
@Component
public class AutoImagingTask extends WordProcessor<WorkflowEvent> {

    @Resource
    private MediaCreatorService mediaCreatorService;

    public AutoImagingTask(
        EnglishWordService englishWordService,
        WordStatusChangeService wordStatusChangeService,
        CozeManager cozeManager
    ) {
        super(englishWordService, wordStatusChangeService, cozeManager);
    }

    @Async
    @Scheduled(fixedDelay = 15 * 1000)
    public void execute() {
        startProcess(WordStatus.STRUCTURED.name(), WordStatus.IMAGING.name(), "AI_AUTO_IMAGING", cozeManager.getConfig().getWorkflowId());
    }

    @Override
    protected void processing(String comment, String botId, EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey) {
        long startTime = System.currentTimeMillis();
        timer.start(timerKey + "_NODE_START");

        // 判断单词数据是否正确
        String info = word.getInfo();

        if (!EnglishWord.isStandardFormat(info)) {
            log.error("Error processing word: {} - {}", word.getWord_head(), "INVALID_FORMAT");
            word.setStatus(WordStatus.FAILED.name());
            onMessageCompleted(comment, -1, word, change, jsonObject, timerKey);
            return;
        }

        JSONObject infoJson = JSONUtil.parseObj(info);
        JSONArray img = infoJson.getJSONArray("img");

        if (img == null) {
            log.error("Error processing word: {} - {}", word.getWord_head(), "NO_IMG_FIELD_PROP");
            word.setStatus(WordStatus.FAILED.name());
            onMessageCompleted(comment, -1, word, change, jsonObject, timerKey);
            return;
        }

        // 如果已经有图片 直接跳过 - 因为这个流程必定会经过结构化 非法图片都被去除了
        if (!img.isEmpty()) {
            word.setStatus(WordStatus.PROCESSED.name());
            onMessageCompleted(comment, -1, word, change, jsonObject, timerKey);
            return;
        }

        MediaCreator mediaCreator = new MediaCreator();
        mediaCreator.setMedia_type(MediaType.IMAGE.name());
        mediaCreator.setWord_id(word.getId());

        mediaCreatorService.save(mediaCreator);

        Map<String, Object> data = new HashMap<>();
        data.put("input", word.getWord_head());

        JSONObject jsonObj = JSONUtil.createObj();
        JSONArray jsonArray = new JSONArray();

        jsonObj.putOnce("input", word.getWord_head());

        Flowable<WorkflowEvent> resp = cozeManager.startWorkflow(botId, data);

        resp.blockingForEach(
                event -> {
                    handleChatEvent(event, word, change, jsonObject, timerKey, comment);

                    long nodeTime = timer.interval(timerKey + "_NODE_START");
                    timer.start(timerKey + "_NODE_START");
                    if (event.getEvent().equals(WorkflowEventType.MESSAGE)) {
                        // 计算每个节点的耗时

                        JSONObject node = JSONUtil.createObj();

                        node.putOnce("event", event.getEvent().getValue());
                        node.putOnce("message", event.getMessage());
                        node.putOnce("costs", nodeTime);

                        if (Objects.equals(event.getMessage().getNodeTitle(), "End")) {
                            JSONObject entries = JSONUtil.parseObj(event.getMessage().getContent());
                            String output = entries.getStr("output");

                            if (StringUtils.isNotBlank(output)) {
                                node.putOnce("output", output);
                                jsonArray.add(node);

                                mediaCreator.setMedia_url(output);

                                img.add(output);
                                infoJson.set("img", img);
                                word.setInfo(JSONUtil.toJsonStr(infoJson));
                            }
                        }

                        jsonArray.add(node);
                    } else {
                        if (event.getEvent().equals(WorkflowEventType.DONE)) {
                            jsonObj.putOnce("workflow", jsonArray);
                            jsonObj.putOnce("status", "DONE");

                        } else if (event.getEvent().equals(WorkflowEventType.ERROR)) {
                            jsonObj.putOnce("status", "ERROR");
                            jsonObj.putOnce("error", event.getError());

                            WorkflowEventError error = event.getError();

                            int code = error.getErrorCode();
                            String msg = error.getErrorMessage();

                            log.error("Error processing word: {} - {}", word.getWord_head(), code + " | " + msg);
                            word.setStatus(WordStatus.FAILED.name());
                        }  else if (event.getEvent().equals(WorkflowEventType.INTERRUPT)) {
                            jsonObj.putOnce("status", "INTERRUPT");

                            log.error("Error processing word: {} - {}", word.getWord_head(), "INTERRRUPT");
                            word.setStatus(WordStatus.FAILED.name());
                        }

                        long endTime = System.currentTimeMillis();

                        jsonObj.putOnce("log_id", event.getLogID());
                        jsonObj.putOnce("costs", endTime - startTime);

                        mediaCreator.setInfo(jsonObj.toString());

                        jsonObject.set("info", mediaCreator.getInfo());

                        change.setInfo(jsonObject.toString());

                        mediaCreatorService.updateById(mediaCreator);

                        word.setStatus(WordStatus.PROCESSED.name());

                        onMessageCompleted(comment, -1, word, change, jsonObject, timerKey);
                    }
                });
    }

    @Override
    protected void handleChatEvent(WorkflowEvent event, EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment) {
    }

    @Override
    protected void handleChatCompleted(EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment) {

    }
}
