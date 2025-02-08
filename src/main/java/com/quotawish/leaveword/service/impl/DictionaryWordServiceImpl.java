package com.quotawish.leaveword.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.mapper.DictionaryWordMapper;
import com.quotawish.leaveword.model.dto.english.dictionary_word.DictionaryWordQueryRequest;
import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.model.enums.WordStatus;
import com.quotawish.leaveword.model.vo.english.DictionaryWordVO;
import com.quotawish.leaveword.service.DictionaryWordService;
import com.quotawish.leaveword.service.UserService;
import com.quotawish.leaveword.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 词典单词表服务实现
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Service
@Slf4j
public class DictionaryWordServiceImpl extends ServiceImpl<DictionaryWordMapper, DictionaryWord> implements DictionaryWordService {

    @Resource
    private UserService userService;


    /**
     * 获取查询条件
     *
     * @param dictionary_wordQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<DictionaryWord> getQueryWrapper(DictionaryWordQueryRequest dictionary_wordQueryRequest) {
        QueryWrapper<DictionaryWord> queryWrapper = new QueryWrapper<>();
        if (dictionary_wordQueryRequest == null) {
            return queryWrapper;
        }

        Long id = dictionary_wordQueryRequest.getId();
        Long notId = dictionary_wordQueryRequest.getNotId();

        String sortField = dictionary_wordQueryRequest.getSortField();
        String sortOrder = dictionary_wordQueryRequest.getSortOrder();

        Long dictionaryId = dictionary_wordQueryRequest.getDictionary_id();
        Long wordId = dictionary_wordQueryRequest.getWord_id();

        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(dictionaryId), "dictionary_id", dictionaryId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(wordId), "word_id", wordId);

        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取词典单词表封装
     *
     * @param dictionary_word
     * @param request
     * @return
     */
    @Override
    public DictionaryWordVO getDictionaryWordVO(DictionaryWord dictionary_word, HttpServletRequest request) {
        // 对象转封装类
        DictionaryWordVO dictionary_wordVO = DictionaryWordVO.objToVo(dictionary_word);

        return dictionary_wordVO;
    }

    /**
     * 分页获取词典单词表封装
     *
     * @param dictionary_wordPage
     * @param request
     * @return
     */
    @Override
    public Page<DictionaryWordVO> getDictionaryWordVOPage(Page<DictionaryWord> dictionary_wordPage, HttpServletRequest request) {
        List<DictionaryWord> dictionary_wordList = dictionary_wordPage.getRecords();
        Page<DictionaryWordVO> dictionary_wordVOPage = new Page<>(dictionary_wordPage.getCurrent(), dictionary_wordPage.getSize(), dictionary_wordPage.getTotal());
        if (CollUtil.isEmpty(dictionary_wordList)) {
            return dictionary_wordVOPage;
        }
        // 对象列表 => 封装对象列表
        List<DictionaryWordVO> dictionary_wordVOList = dictionary_wordList.stream().map(dictionary_word -> {
            return DictionaryWordVO.objToVo(dictionary_word);
        }).collect(Collectors.toList());

        dictionary_wordVOPage.setRecords(dictionary_wordVOList);
        return dictionary_wordVOPage;
    }

    @Override
    public int[] batchRelativeDictionaryWord(Long dictionary_id, Long[] words) {
        Stream<DictionaryWord> dwStream = Arrays.stream(words).map(word -> {
            DictionaryWord dw = new DictionaryWord();

            dw.setDictionary_id(dictionary_id);
            dw.setWord_id(word);

            return dw;
        });

        List<DictionaryWord> englishWordList = dwStream.collect(Collectors.toList());

        // 使用分页查询来获取 existingDictionaryWordHeads
        int pageSize = 1000; // 每页大小
        int totalWords = englishWordList.size();
        Set<Long> existingWordHeads = new HashSet<>();

        for (int offset = 0; offset < totalWords; offset += pageSize) {
            int limit = Math.min(pageSize, totalWords - offset);
            List<Long> batchWordId = englishWordList.stream()
                    .skip(offset)
                    .limit(limit)
                    .map(DictionaryWord::getWord_id)
                    .collect(Collectors.toList());

            List<Long> existingBatchWordHeads = getBaseMapper().selectObjs(
                    new QueryWrapper<DictionaryWord>().select("word_id").in("word_id", batchWordId)
                            .eq("status", WordStatus.APPROVED)
            ).stream().map(o -> (Long) o).collect(Collectors.toList());

            existingWordHeads.addAll(existingBatchWordHeads);
        }

        List<DictionaryWord> filteredEnglishWordList = englishWordList.stream()
                .filter(englishWord -> !existingWordHeads.contains(englishWord.getWord_id()))
                .collect(Collectors.toList());

        if (filteredEnglishWordList.isEmpty()) {
            log.info("All words already exist in the database.");
            return new int[] {0, existingWordHeads.size(), 0};
        }

        // 使用批量插入时检查是否已经存在
        List<DictionaryWord> successfullyInsertedWords = new ArrayList<>();
        List<DictionaryWord> failedInsertedWords = new ArrayList<>();

        for (DictionaryWord englishWord : filteredEnglishWordList) {
            try {
                getBaseMapper().insert(englishWord);
                successfullyInsertedWords.add(englishWord);
            } catch (DuplicateKeyException e) {
                failedInsertedWords.add(englishWord);
            }
        }

        int successfulInserts = successfullyInsertedWords.size();
        int existingWords = existingWordHeads.size();
        int failedInserts = failedInsertedWords.size();

        log.info("Successfully inserted {} words relationship.", successfulInserts);
        log.info("Failed to insert {} words relationship.", failedInserts);

        return new int[] {successfulInserts, existingWords, failedInserts};
    }

    @Override
    public List<DictionaryWord> listDictionaryWordBatch(Long dictionary_id) {
        return baseMapper.selectList(new QueryWrapper<DictionaryWord>().eq("dictionary_id", dictionary_id));
    }

}
