package com.quotawish.leaveword.job.cycle;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.client.workflows.run.model.WorkflowEventError;
import com.coze.openapi.client.workflows.run.model.WorkflowEventType;
import com.quotawish.leaveword.manager.CozeManager;
import com.quotawish.leaveword.model.entity.audio.MediaCreator;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordContent;
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

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 本类做的事情就一个
 * TODO 把word的info数据中多余的图片/音频删除 不正确的例句矫正
 */
@Slf4j
@Component
public class AutoStructureTask extends WordProcessor<WorkflowEvent> {

    public AutoStructureTask(
        EnglishWordService englishWordService,
        WordStatusChangeService wordStatusChangeService,
        CozeManager cozeManager
    ) {
        super(englishWordService, wordStatusChangeService, cozeManager);
    }

    @Async
    @Scheduled(fixedDelay = 10000)
    public void execute() {
        startProcess(WordStatus.SUPPLIED.name(), WordStatus.STRUCTURE_FIXING.name(), "AI_AUTO_STRUCT_FIXING", null);
    }

    @Override
    protected void processing(String comment, String botId, EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey) {
        // 判断单词数据是否正确
        String info = word.getInfo();
        WordContent wordContent;

        try {
            if (!EnglishWord.isStandardFormat(info)) {
                throw new Exception("INVALID_FORMAT");
            }

            wordContent = word.transformInfo();

        } catch ( Exception e ) {
            log.error("Error structure fixing word(auto status or parse): {} - {}", word.getWord_head(), e.getMessage());
            word.setStatus(WordStatus.DATA_FORMAT_ERROR.name());
            onMessageCompleted(comment, -1, word, change, jsonObject, timerKey);
            return;
        }

        if (wordContent == null) {
            log.error("Error processing word: {} - {}", word.getWord_head(), "INVALID_FORMAT");
            word.setStatus(WordStatus.FAILED.name());
            onMessageCompleted(comment, -1, word, change, jsonObject, timerKey);
            return;
        }

        WordContent wordContent1 = WordContent.structFixing(wordContent);

        jsonObject.put("beforeInfo", info);

        word.setInfo(JSONUtil.toJsonStr(wordContent1));

        word.setStatus(WordStatus.STRUCTURED.name());

        onMessageCompleted(comment, -1, word, change, jsonObject, timerKey);
    }

    @Override
    protected void handleChatEvent(WorkflowEvent event, EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment) {
    }

    @Override
    protected void handleChatCompleted(EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment) {

    }
}
