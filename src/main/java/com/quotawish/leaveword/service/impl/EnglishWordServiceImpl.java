package com.quotawish.leaveword.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.mapper.DictionaryWordMapper;
import com.quotawish.leaveword.mapper.EnglishWordMapper;
import com.quotawish.leaveword.mapper.UserMapper;
import com.quotawish.leaveword.model.dto.english.english_word.*;
import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.model.enums.WordStatus;
import com.quotawish.leaveword.model.vo.english.DictionaryWordWithWordVO;
import com.quotawish.leaveword.model.vo.english.EnglishWordVO;
import com.quotawish.leaveword.service.DictionaryWordService;
import com.quotawish.leaveword.service.EnglishWordService;
import com.quotawish.leaveword.service.UserService;
import com.quotawish.leaveword.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 英语单词服务实现
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Service
@Slf4j
public class EnglishWordServiceImpl extends ServiceImpl<EnglishWordMapper, EnglishWord> implements EnglishWordService {

    @Resource
    private DictionaryWordService dwService;

    @Autowired
    private DictionaryWordMapper dwMapper;

    @Resource
    private WordStatusChangeServiceImpl statusChangeService;

    /**
     * 获取查询条件
     *
     * @param english_wordQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<EnglishWord> getQueryWrapper(EnglishWordQueryRequest english_wordQueryRequest) {
        QueryWrapper<EnglishWord> queryWrapper = new QueryWrapper<>();
        if (english_wordQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = english_wordQueryRequest.getId();
        Long notId = english_wordQueryRequest.getNotId();
//        String title = english_wordQueryRequest.getTitle();
//        String content = english_wordQueryRequest.getStatus();
        String status = english_wordQueryRequest.getStatus();
        String searchText = english_wordQueryRequest.getSearchText();
        String sortField = english_wordQueryRequest.getSortField();
        String sortOrder = english_wordQueryRequest.getSortOrder();
        Long userId = english_wordQueryRequest.getUserId();
        // todo 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("title", searchText).or().like("content", searchText));
        }
        // 模糊查询
//        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);

        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(StringUtils.isNotBlank(status), "status", status);

        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public IPage<DictionaryWordWithWordVO> getQueryWrapper(EnglishWordQueryDictRequest request) {
        Long id = request.getId();
        Long notId = request.getNotId();
        String status = request.getStatus();
        String searchText = request.getSearchText();
        String sortField = request.getSortField();
        String sortOrder = request.getSortOrder();

        MPJLambdaWrapper<DictionaryWordWithWordVO> wrapper = new MPJLambdaWrapper<DictionaryWordWithWordVO>()
                .selectAll(DictionaryWord.class)
                .selectAll(EnglishWord.class)
                .leftJoin(EnglishWord.class, EnglishWord::getId, DictionaryWord::getWord_id)
//                .selectAs(EnglishWord.class, DictionaryWordWithWordVO::getWord)
                .eq(DictionaryWord::getDictionary_id, request.getDict_id())
                ;

        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            wrapper.and(qw -> qw.like(EnglishWord::getWord_head, searchText).or().like(EnglishWord::getInfo, searchText));
        }

        // 精确查询
        wrapper.ne(ObjectUtils.isNotEmpty(notId), DictionaryWord::getWord_id, notId);
        wrapper.eq(ObjectUtils.isNotEmpty(id), DictionaryWord::getWord_id, id);
        wrapper.eq(StringUtils.isNotBlank(status), EnglishWord::getStatus, status);

        wrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), DictionaryWord::getCreated_at);

        IPage<DictionaryWordWithWordVO> dictionaryWordWithWordVOIPage = dwMapper.selectJoinPage(new Page<>(request.getCurrent(), request.getPageSize()), DictionaryWordWithWordVO.class, wrapper);

        return dictionaryWordWithWordVOIPage;
    }

    /**
     * 获取英语单词封装
     *
     * @param english_word
     * @param request
     * @return
     */
    @Override
    public EnglishWordVO getEnglishWordVO(EnglishWord english_word, HttpServletRequest request) {
        // 对象转封装类
        EnglishWordVO english_wordVO = EnglishWordVO.objToVo(english_word);

        return english_wordVO;
    }

    /**
     * 分页获取英语单词封装
     *
     * @param english_wordPage
     * @param request
     * @return
     */
    @Override
    public Page<EnglishWordVO> getEnglishWordVOPage(Page<EnglishWord> english_wordPage, HttpServletRequest request) {
        List<EnglishWord> english_wordList = english_wordPage.getRecords();
        Page<EnglishWordVO> english_wordVOPage = new Page<>(english_wordPage.getCurrent(), english_wordPage.getSize(), english_wordPage.getTotal());
        if (CollUtil.isEmpty(english_wordList)) {
            return english_wordVOPage;
        }
        // 对象列表 => 封装对象列表
        List<EnglishWordVO> english_wordVOList = english_wordList.stream().map(english_word -> {
            return EnglishWordVO.objToVo(english_word);
        }).collect(Collectors.toList());

        english_wordVOPage.setRecords(english_wordVOList);
        return english_wordVOPage;
    }

    @Override
    public int[] batchImportEnglishWord(EnglishWordAddBatchRequest request) {
        Collection<EnglishWordAddRequest> words = request.getWords();

        Stream<EnglishWord> englishWordStream = words.stream().map(word -> {
            EnglishWord english_word = new EnglishWord();

            english_word.setWord_head(word.getWord_head());
            english_word.setInfo(word.getInfo());

            if (english_word.getInfo() == null) {
                english_word.setStatus(WordStatus.CREATED.name());
                return english_word;
            }

            // 进行初步校验english word 符合指定格式标记一下
            try {
                boolean standardFormat = EnglishWord.isStandardFormat(word.getInfo());

                if (standardFormat) {
                    english_word.setStatus(WordStatus.PROCESSED.name());
                } else {
                    english_word.setStatus(WordStatus.DATA_FORMAT_ERROR.name());
                }

            } catch (Exception e) {
                english_word.setStatus(WordStatus.UNKNOWN.name());
            }

            return english_word;
        });

// 把 englishWordStream 转成list
        List<EnglishWord> englishWordList = englishWordStream.collect(Collectors.toList());

// 使用分页查询来获取 existingWordHeads
        int pageSize = 1000; // 每页大小
        int totalWords = englishWordList.size();
        Set<String> existingWordHeads = new HashSet<>();

        for (int offset = 0; offset < totalWords; offset += pageSize) {
            int limit = Math.min(pageSize, totalWords - offset);
            List<String> batchWordHeads = englishWordList.stream()
                    .skip(offset)
                    .limit(limit)
                    .map(EnglishWord::getWord_head)
                    .collect(Collectors.toList());

            List<String> existingBatchWordHeads = getBaseMapper().selectObjs(
                    new QueryWrapper<EnglishWord>().select("word_head").in("word_head", batchWordHeads)
            ).stream().map(Object::toString).collect(Collectors.toList());

            existingWordHeads.addAll(existingBatchWordHeads);
        }

        List<EnglishWord> filteredEnglishWordList = englishWordList.stream()
                .filter(englishWord -> !existingWordHeads.contains(englishWord.getWord_head()))
                .collect(Collectors.toList());

        if (filteredEnglishWordList.isEmpty()) {
            log.info("All words already exist in the database.");
            return new int[] {0, existingWordHeads.size(), 0};
        }

// 使用批量插入时检查是否已经存在
        List<EnglishWord> successfullyInsertedWords = new ArrayList<>();
        List<EnglishWord> failedInsertedWords = new ArrayList<>();

        for (EnglishWord englishWord : filteredEnglishWordList) {
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

        log.info("Successfully inserted {} words.", successfulInserts);
        log.info("Failed to insert {} words.", failedInserts);

        return new int[] {successfulInserts, existingWords, failedInserts};

    }

    @Override
    public Long[] batchGetEnglishWordId(EnglishWordGetBatchRequest request) {
        Collection<String> words = request.getWords();

        return getBaseMapper().selectObjs(
                new QueryWrapper<EnglishWord>().select("id").in("word_head", words)
        ).stream().map(Object::toString).map(Long::parseLong).toArray(Long[]::new);
    }

    @Override
    public boolean scoreEnglishWord(EnglishWordScoreRequest request) {
        EnglishWord byId = getById(request.getId());
        ThrowUtils.throwIf(byId == null, ErrorCode.NOT_FOUND_ERROR);

        Integer score = request.getScore();
        Integer aiScore = request.getAiScore();

        double totalScore = score * 0.4 + aiScore * 0.6;
        boolean passValidate = totalScore > 85;

        WordStatusChange wordStatusChange = new WordStatusChange();

        wordStatusChange.setWordId(request.getId());
        wordStatusChange.setComment(passValidate ? "评分通过审核" : "评分未通过审核");
        wordStatusChange.setInfo(request.getAiContent());

        byId.setStatus(passValidate ? WordStatus.APPROVED.name() : WordStatus.REJECTED.name());
        wordStatusChange.setStatus(byId.getStatus());

        updateById(byId);
        statusChangeService.save(wordStatusChange);

        return passValidate;
    }

    @Override
    public WordStatusChange getEnglishWordAutoScore(Long id) {
        WordStatusChange change = statusChangeService.getOne(new QueryWrapper<WordStatusChange>().eq("word_id", id).eq("COMMENT", "AI_AUTO_RATE").last("LIMIT 1"));

        return change;
    }

}
