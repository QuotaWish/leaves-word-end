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
public class AutoRateTask extends WordProcessor<ChatEvent> {

    public AutoRateTask(
        EnglishWordService englishWordService,
        WordStatusChangeService wordStatusChangeService,
        CozeManager cozeManager
    ) {
        super(englishWordService, wordStatusChangeService, cozeManager);
    }

    @Async
    @Scheduled(fixedDelay = 15 * 1000)
    public void execute() {
        startProcess(WordStatus.WAIT_FOR_AI_REVIEW.name(), WordStatus.REVIEWING.name(), "AI_AUTO_RATE", cozeManager.getConfig().getValidateId());
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
                    int score = json.getInt("score", 0);
//                    word.setStatus(score < 75 ? WordStatus.REJECTED.name() : WordStatus.UPLOADED.name());
                    word.setAi_score(score);
                } catch (Exception e) {
                    log.error("Error processing word: {}", word.getWord_head(), e);
                    word.setStatus(WordStatus.FAILED.name());
                }
            }
        }
    }

    @Override
    protected void handleChatCompleted(EnglishWord word, WordStatusChange change, JSONObject jsonObject, String timerKey, String comment) {
        int score = word.getManual_score();
        int aiScore = word.getAi_score();

        double totalScore = score * 0.4 + aiScore * 0.6;
        boolean passValidate = totalScore > 85;

        WordStatusChange wordStatusChange = new WordStatusChange();

        wordStatusChange.setWordId(word.getId());
        wordStatusChange.setComment(passValidate ? "评分通过审核" : "评分未通过审核");
        wordStatusChange.setInfo(jsonObject.getStr("info"));

        word.setStatus(passValidate ? WordStatus.APPROVED.name() : WordStatus.REJECTED.name());
        wordStatusChange.setStatus(word.getStatus());

        getWordStatusChangeService().save(wordStatusChange);
    }
}
