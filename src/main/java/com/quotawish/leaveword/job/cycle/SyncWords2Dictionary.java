package com.quotawish.leaveword.job.cycle;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.quotawish.leaveword.esdao.PostEsDao;
import com.quotawish.leaveword.mapper.DictionaryWordMapper;
import com.quotawish.leaveword.mapper.PostMapper;
import com.quotawish.leaveword.model.dto.english.english_word.EnglishWordQueryRequest;
import com.quotawish.leaveword.model.dto.post.PostEsDTO;
import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.model.entity.Post;
import com.quotawish.leaveword.model.entity.english.EnglishDictionary;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.enums.WordStatus;
import com.quotawish.leaveword.model.vo.english.DictionaryWordWithWordVO;
import com.quotawish.leaveword.service.DictionaryWordService;
import com.quotawish.leaveword.service.EnglishDictionaryService;
import com.quotawish.leaveword.service.EnglishWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 同步单词数量到词典信息下
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Component
@Slf4j
public class SyncWords2Dictionary {

    @Resource
    private EnglishDictionaryService edService;

    @Resource
    private DictionaryWordMapper dwMapper;

//    @Resource
//    private EnglishWordService ewService;

    /**
     * 每 8 小时执行一次同步
     */
    @Scheduled(fixedRate = 8 * 60 * 60 * 1000)
    public void run() {
        log.info("start to sync words to dictionary");

        // 获取每一个词典
        edService.list().forEach(ed -> {
            // 获取对应的单词数量 需要不同状态的：已关联的 / 已发布的 / 已审核通过的
            // 首先 获取已关联的单词数量
            long totalAmount = dwMapper.selectCount(new MPJLambdaWrapper<DictionaryWord>().eq("dictionary_id", ed.getId()));

            long publishedAmount = dwMapper.selectJoinCount(amount(ed.getId(), WordStatus.PUBLISHED));
            long approvedAmount = dwMapper.selectJoinCount(amount(ed.getId(), WordStatus.APPROVED));

            // 使用UpdateWrapper指定需要更新的字段
            UpdateWrapper<EnglishDictionary> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", ed.getId())
                    .set("total_words", totalAmount)
                    .set("published_words", publishedAmount)
                    .set("approved_words", approvedAmount);

            boolean result = edService.update(updateWrapper);

            if (!result) {
                log.error("词典 {} 更新失败", ed.getId());
            } else {
                log.info("词典 {} 已关联的单词数量：{}，已发布的单词数量：{}，已审核通过的单词数量：{}", ed.getId(), totalAmount, publishedAmount, approvedAmount);
            }

//            ed.setTotal_words((int) totalAmount);
//            ed.setPublished_words((int) publishedAmount);
//            ed.setApproved_words((int) approvedAmount);
//
//            edService.updateById(ed);


        });
    }

    /**
     * 获取某个词典中处于某个状态的单词查询条件
     * @param id 词典
     * @param status 状态
     * @return 对应查询条件
     */
    private static MPJLambdaWrapper<DictionaryWord> amount(long id, WordStatus status) {

        return new MPJLambdaWrapper<>(DictionaryWord.class)
                .select(DictionaryWord::getId)
//                .selectAll(EnglishWord.class)
                .selectAssociation(EnglishWord.class, DictionaryWordWithWordVO::getWord)
                .leftJoin(EnglishWord.class, EnglishWord::getId, DictionaryWord::getWord_id)
                .eq(DictionaryWord::getDictionary_id, id)
                .eq(EnglishWord::getStatus, status.name());
    }
}
