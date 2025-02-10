package com.quotawish.leaveword.utils.task;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.service.CozeAPI;
import com.quotawish.leaveword.manager.CozeManager;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.service.EnglishWordService;
import com.quotawish.leaveword.service.WordStatusChangeService;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Date;

@Slf4j
public abstract class WordProcessor<T extends BaseResp> {

    public static TimeInterval timer = new TimeInterval();

    @Getter
    protected final EnglishWordService englishWordService;

    @Getter
    protected final WordStatusChangeService wordStatusChangeService;

    @Getter
    protected final CozeManager cozeManager;

    public WordProcessor(
        EnglishWordService englishWordService, 
        WordStatusChangeService wordStatusChangeService, 
        CozeManager cozeManager
    ) {
        this.englishWordService = englishWordService;
        this.wordStatusChangeService = wordStatusChangeService;
        this.cozeManager = cozeManager;
    }

    public void startProcess(String initialStatus, String processingStatus, String comment, String botId) {
        EnglishWord word = englishWordService.getOne(new QueryWrapper<EnglishWord>().eq("status", initialStatus).last("LIMIT 1"));
        if (word == null) {
            return;
        }

        word.setStatus(processingStatus);
        englishWordService.updateById(word);

        log.info("Processing word({}): {}", comment, word.getWord_head());

        String timerKey = "SYSTEM_AUTO_" + word.getWord_head();
        timer.start(timerKey);

        JSONObject jsonObject = new JSONObject();
        jsonObject.set("beforeStatus", initialStatus);

        WordStatusChange change = new WordStatusChange();

        change.setComment(comment);
        change.setWordId(word.getId());


        processing(comment, botId, word, change, jsonObject, timerKey);

    }

    protected void processing(String comment, String botId, EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey) {
        CozeAPI cozeApi = cozeManager.getCozeApi();
        CreateChatReq req = CreateChatReq.builder()
                .botID(botId)
                .userID("SYSTEM_AUTO")
                .messages(Collections.singletonList(Message.buildUserQuestionText(word.getInfo())))
                .build();

        Flowable<ChatEvent> resp = cozeApi.chat().stream(req);
        resp.blockingForEach(event -> {
            handleChatEvent((T) event, word, change, jsonObject, timerKey, comment);

            if (event.getEvent().equals(ChatEventType.CONVERSATION_CHAT_COMPLETED)) {
                onMessageCompleted(comment, event.getChat().getUsage().getTokenCount(), word, change, jsonObject, timerKey);
            }
        });
    }

    protected void onMessageCompleted(String comment, int tokenCount, EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey) {
        long intervalMs = timer.intervalMs(timerKey);
        long interval = intervalMs / 1000 / 60;

        if (interval >= 3) {
            log.warn("Processing word: {} time too long", word.getWord_head());
        }

        log.info("Processing word: {} completed", word.getWord_head());

        handleChatCompleted(word, change, jsonObject, timerKey, comment);

        jsonObject.set("interval", intervalMs);
        jsonObject.set("afterStatus", change.getStatus());
        jsonObject.set("tokens", tokenCount);
        change.setStatus(word.getStatus());
        change.setInfo(jsonObject.toString());
        change.setUpdateTime(new Date());

        wordStatusChangeService.saveOrUpdate(change);
        englishWordService.updateById(word);
    }

    protected abstract void handleChatEvent(T event, EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment);

    protected abstract void handleChatCompleted(EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment);
}
