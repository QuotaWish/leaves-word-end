package com.quotawish.leaveword.job.cycle;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.MessageType;
import com.quotawish.leaveword.manager.CozeManager;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.model.enums.WordStatus;
import com.quotawish.leaveword.service.EnglishWordService;
import com.quotawish.leaveword.service.WordStatusChangeService;
import com.quotawish.leaveword.utils.task.WordProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AutoSupplyTask extends WordProcessor<ChatEvent> {

    public AutoSupplyTask(
        EnglishWordService englishWordService,
        WordStatusChangeService wordStatusChangeService,
        CozeManager cozeManager
    ) {
        super(englishWordService, wordStatusChangeService, cozeManager);
    }

    @Async
    @Scheduled(fixedDelay = 60 * 1000)
    public void execute() {
        startProcess(WordStatus.UNKNOWN.name(), WordStatus.SUPPLYING.name(), "AI_AUTO_SUPPLEMENT", cozeManager.getConfig().getSupplyId());
    }

    @Override
    protected void handleChatEvent(ChatEvent event, EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment) {
        if (ChatEventType.CONVERSATION_MESSAGE_COMPLETED.equals(event.getEvent())) {
            if (event.getMessage().getType() == MessageType.ANSWER) {
                String totalInfo = event.getMessage().getContent();

                jsonObject.set("info", totalInfo);

                change.setInfo(jsonObject.toString());

                try {
                    JSONObject json = JSONUtil.parseObj(totalInfo);
                    word.setInfo(json.getStr("data"));
                    word.setStatus(WordStatus.SUPPLIED.name());
                } catch (Exception e) {
                    log.error("Error processing word: {}", word.getWord_head(), e);
                    word.setStatus(WordStatus.FAILED.name());
                }
            }
        }
    }

    @Override
    protected void handleChatCompleted(EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment) {

    }
}
