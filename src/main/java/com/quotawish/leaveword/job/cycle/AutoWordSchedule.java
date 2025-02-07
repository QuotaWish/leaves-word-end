package com.quotawish.leaveword.job.cycle;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageType;
import com.coze.openapi.service.service.CozeAPI;
import com.quotawish.leaveword.esdao.PostEsDao;
import com.quotawish.leaveword.manager.CozeManager;
import com.quotawish.leaveword.mapper.PostMapper;
import com.quotawish.leaveword.model.dto.post.PostEsDTO;
import com.quotawish.leaveword.model.entity.Post;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.model.enums.WordStatus;
import com.quotawish.leaveword.service.EnglishWordService;
import com.quotawish.leaveword.service.WordStatusChangeService;
import com.quotawish.leaveword.utils.StringUtil;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;

/**
 * 本类主要功能如下：
 * 1. 每5分钟自动润色一个单词
 * TODO 2. 每15分钟设置单词最佳的关联词 (PLAN)
 */
@Slf4j
 @Component
public class AutoWordSchedule {

    @Resource
    private EnglishWordService englishWordService;

    @Resource
    private WordStatusChangeService wordStatusChangeService;

    @Resource
    private CozeManager cozeManager;

    static TimeInterval timer = new TimeInterval();


    /**
     * 每30s执行一次
     */
    @Async
    @Scheduled(fixedDelay = 30 * 1000)
    public void autoRate() {
        // 取出一个已处理的单词 （创建的单词不支持）
        EnglishWord word = englishWordService.getOne(new QueryWrapper<EnglishWord>().eq("status", WordStatus.PROCESSED).last("LIMIT 1"));
        if (word == null) {
            return;
        }

        word.setStatus(WordStatus.REVIEWING.name());
        englishWordService.updateById(word);

        log.info("正在审核单词(rating)：{}", word.getWord_head());

        String currentInfo = word.getInfo();
        if (StrUtil.isBlankIfStr(currentInfo)) {
            log.info("单词(rating)信息为空 - 已跳过：{}", word.getWord_head());

            word.setStatus(WordStatus.DATA_FORMAT_ERROR.name());
            englishWordService.updateById(word);

            return;
        }

        CozeAPI cozeApi = cozeManager.getCozeApi();

        CreateChatReq req =
                CreateChatReq.builder()
                        .botID(cozeManager.getConfig().getValidateId())
                        .userID("SYSTEM_AUTO_SUPPLY")
                        .messages(Collections.singletonList(Message.buildUserQuestionText(currentInfo)))
                        .build();

        // 记录耗时 超过2分钟就提醒
        String timerKey = "SYSTEM_AUTO_RATE_" + word.getWord_head();

        timer.start(timerKey);

        WordStatusChange change;

        // 先找一下change
        change = wordStatusChangeService.getOne(new QueryWrapper<WordStatusChange>().eq("word_id", word.getId()).eq("COMMENT", "AI_AUTO_RATE").last("LIMIT 1"));
        if ( change == null ) {
            change = new WordStatusChange();
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.set("beforeStatus", change.getStatus());

        Flowable<ChatEvent> resp = cozeApi.chat().stream(req);
        WordStatusChange finalChange = change;
        resp.blockingForEach(
                event -> {
                    if (ChatEventType.CONVERSATION_MESSAGE_COMPLETED.equals(event.getEvent())) {
                        if ( event.getMessage().getType() == MessageType.ANSWER ) {
                            long intervalMs = timer.intervalMs(timerKey);
                            long interval = intervalMs / 1000 / 60;
                            String totalInfo = event.getMessage().getContent();


                            jsonObject.set("interval", intervalMs);

                            finalChange.setWordId(word.getId());
                            finalChange.setInfo(jsonObject.toString());
                            finalChange.setComment("AI_AUTO_RATE");

                            try {

                                JSONObject json = JSONUtil.parseObj(totalInfo);

                                Object code = json.getOrDefault("code", null);

                                if (code == null || (Integer.parseInt(String.valueOf(code)) != 200)) {
                                    throw new Exception("code error | " + code);
                                }

                                word.setInfo(totalInfo);

                                int score = json.getInt("score", 0);

                                // 如果评分 75 都没有
                                if ( score < 75 ) {
                                    word.setStatus(WordStatus.REJECTED.name());
                                } else {
                                    // 表示已上传处理
                                    word.setStatus(WordStatus.UPLOADED.name());
                                }

                            } catch (Exception e) {
                                log.error("审核单词出错：{}", word.getWord_head(), e);
                                word.setStatus(WordStatus.FAILED.name());
                            } finally {
                                if (interval >= 3) {
                                    log.warn("审核单词耗时较久：{}", word.getWord_head());
                                }

                                finalChange.setStatus(word.getStatus());
                                jsonObject.set("afterStatus", finalChange.getStatus());
                                finalChange.setInfo(jsonObject.toString());

                                finalChange.setUpdateTime(new Date());

                                wordStatusChangeService.saveOrUpdate(finalChange);
                                englishWordService.updateById(word);
                            }

                        }
                    }
                    if (ChatEventType.CONVERSATION_CHAT_COMPLETED.equals(event.getEvent())) {
                        log.info("处理完成 | Using token: {}", event.getChat().getUsage().getTokenCount());

                        jsonObject.set("tokens", event.getChat().getUsage().getTokenCount());
                        finalChange.setInfo(jsonObject.toString());

                        wordStatusChangeService.save(finalChange);
                    }
                });
    }

    /**
     * 每30s执行一次
     */
    @Async
    @Scheduled(fixedDelay = 30 * 1000)
    public void autoSupply() {
        // 取出一个导入的单词 （创建的单词不支持）
        EnglishWord word = englishWordService.getOne(new QueryWrapper<EnglishWord>().eq("status", WordStatus.UNKNOWN).last("LIMIT 1"));
        if (word == null) {
            return;
        }

        word.setStatus(WordStatus.PROCESSING.name());
        englishWordService.updateById(word);

        log.info("正在处理单词：{}", word.getWord_head());

        // 异步运行llm
        String currentInfo = word.getInfo();

        CozeAPI cozeApi = cozeManager.getCozeApi();

        CreateChatReq req =
                CreateChatReq.builder()
                        .botID(cozeManager.getConfig().getSupplyId())
                        .userID("SYSTEM_AUTO_SUPPLY")
                        .messages(Collections.singletonList(Message.buildUserQuestionText(currentInfo != null ? currentInfo : "")))
                        .build();

        // 记录耗时 超过3分钟就提醒
        String timerKey = "SYSTEM_AUTO_SUPPLY_" + word.getWord_head();

        timer.start(timerKey);

        WordStatusChange change = new WordStatusChange();
        JSONObject jsonObject = new JSONObject();

        jsonObject.set("before", currentInfo);
        jsonObject.set("beforeStatus", change.getStatus());

        Flowable<ChatEvent> resp = cozeApi.chat().stream(req);
        resp.blockingForEach(
                event -> {
                    if (ChatEventType.CONVERSATION_MESSAGE_COMPLETED.equals(event.getEvent())) {
                        if ( event.getMessage().getType() == MessageType.ANSWER ) {
                            long intervalMs = timer.intervalMs(timerKey);
                            long interval = intervalMs / 1000 / 60;
                            String totalInfo = event.getMessage().getContent();


                            jsonObject.set("after", totalInfo);
                            jsonObject.set("interval", intervalMs);

                            change.setWordId(word.getId());
                            change.setInfo(jsonObject.toString());
                            change.setComment("AI_AUTO_SUPPLEMENT");

                            try {

                                JSONObject _entries = JSONUtil.parseObj(totalInfo);

                                word.setInfo(totalInfo);
                                word.setStatus(WordStatus.PROCESSED.name());

                            } catch (Exception e) {
                                log.error("处理单词出错：{}", word.getWord_head(), e);
                                word.setStatus(WordStatus.FAILED.name());
                            } finally {
                                if (interval >= 3) {
                                    log.warn("处理单词耗时较久：{}", word.getWord_head());
                                }

                                change.setStatus(word.getStatus());
                                jsonObject.set("afterStatus", change.getStatus());
                                change.setInfo(jsonObject.toString());

                                change.setUpdateTime(new Date());

                                wordStatusChangeService.save(change);
                                englishWordService.updateById(word);
                            }

                        }
                    }
                    if (ChatEventType.CONVERSATION_CHAT_COMPLETED.equals(event.getEvent())) {
                        log.info("处理完成 | Using token: {}", event.getChat().getUsage().getTokenCount());

                        jsonObject.set("tokens", event.getChat().getUsage().getTokenCount());
                        change.setInfo(jsonObject.toString());

                        wordStatusChangeService.save(change);
                    }
                });
    }

}
