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
import java.util.function.Function;
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
        String searchText = english_wordQueryRequest.getWord_head();
        String sortField = english_wordQueryRequest.getSortField();
        String sortOrder = english_wordQueryRequest.getSortOrder();

        // todo 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("word_head", searchText).or().like("info", searchText));
        }
        // 模糊查询
//        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);

        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
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

        MPJLambdaWrapper<DictionaryWord> wrapper = new MPJLambdaWrapper<DictionaryWord>()
                .selectAll(DictionaryWord.class)
                .selectAll(EnglishWord.class)
                .selectAssociation(EnglishWord.class, DictionaryWordWithWordVO::getWord)
                .leftJoin(EnglishWord.class, EnglishWord::getId, DictionaryWord::getWord_id)
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

        Page<DictionaryWordWithWordVO> objectPage = new Page<>(request.getCurrent(), request.getPageSize());

        IPage<DictionaryWordWithWordVO> dictionaryWordWithWordVOIPage = dwMapper.selectJoinPage(objectPage, DictionaryWordWithWordVO.class, wrapper);

        // 手动映射 EnglishWord 到 DictionaryWordWithWordVO 的 word 字段
//        List<DictionaryWordWithWordVO> records = dictionaryWordWithWordVOIPage.getRecords();
//        for (DictionaryWordWithWordVO vo : records) {
//            EnglishWord englishWord = new EnglishWord(); // 假设这里是从数据库查询出来的
//            vo.setWord(englishWord); // 设置 word 字段
//        }

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
        Collection<EnglishWordAddRequest> reqWords = request.getWords();
        if (reqWords == null || reqWords.isEmpty()) {
            return new int[]{0, 0, 0};
        }

        // 1. 映射并去重输入：按 word_head 保留第一个
        Map<String, EnglishWord> headToWord = reqWords.stream()
                .filter(Objects::nonNull)
                .map(wr -> {
                    EnglishWord ew = new EnglishWord();
                    ew.setWord_head(wr.getWord_head());
                    ew.setInfo(wr.getInfo());
                    // status 逻辑
                    if (wr.getInfo() == null) {
                        ew.setStatus(WordStatus.CREATED.name());
                    } else {
                        try {
                            boolean ok = EnglishWord.isStandardFormat(wr.getInfo());
                            ew.setStatus(ok
                                    ? WordStatus.PROCESSED.name()
                                    : WordStatus.DATA_FORMAT_ERROR.name());
                        } catch (Exception ex) {
                            ew.setStatus(WordStatus.UNKNOWN.name());
                        }
                    }
                    return ew;
                })
                .filter(ew -> ew.getWord_head() != null && !ew.getWord_head().trim().isEmpty())
                .collect(Collectors.toMap(
                        EnglishWord::getWord_head,
                        Function.identity(),
                        (existing, next) -> existing,    // 保留第一个
                        LinkedHashMap::new
                ));

        if (headToWord.isEmpty()) {
            return new int[]{0, 0, 0};
        }

        // 2. 一次性查出所有已存在的 word_head
        Set<String> allHeads = headToWord.keySet();
        List<String> existList = getBaseMapper().selectObjs(
                        new QueryWrapper<EnglishWord>()
                                .select("word_head")
                                .in("word_head", allHeads)
                ).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        Set<String> existSet = new HashSet<>(existList);

        // 3. 过滤出真正需要插入的
        List<EnglishWord> toInsert = headToWord.values().stream()
                .filter(ew -> !existSet.contains(ew.getWord_head()))
                .collect(Collectors.toList());

        int existingCount = existSet.size();
        if (toInsert.isEmpty()) {
            log.info("All {} words already exist.", existingCount);
            return new int[]{0, existingCount, 0};
        }

        // 4. 批量插入
        // 如果是 Service 层，直接用 this.saveBatch；如果是 Mapper，调用 insertBatch
        int batchSize = 500;
        boolean ok = this.saveBatch(toInsert, batchSize);
        int insertedCount = ok ? toInsert.size() : 0;

        log.info("Successfully inserted {} words, {} skipped.", insertedCount, existingCount);
        return new int[]{insertedCount, existingCount, 0};

    }

    @Override
    public List<WordHeadIdDto> batchGetEnglishWordId(EnglishWordGetBatchRequest request) {
        Collection<String> words = request.getWords();
        if (words == null || words.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> uniqueWords = words.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        if (uniqueWords.isEmpty()) {
            return Collections.emptyList();
        }

        List<EnglishWord> list = getBaseMapper().selectList(
                new QueryWrapper<EnglishWord>()
                        // 也可以改成 .distinct() 或者 .select("word_head", "MIN(id) AS id").groupBy("word_head")
                        .select("word_head", "id")
                        .in("word_head", uniqueWords)
        );

        Map<String, WordHeadIdDto> map = new LinkedHashMap<>();
        for (EnglishWord e : list) {
            String head = e.getWord_head();
            // 如果想保留最小 id，可以改成：
            // map.merge(head,
            //     new WordHeadIdDto(head, e.getId()),
            //     (existing, next) -> existing.getId() <= next.getId() ? existing : next
            // );
            map.putIfAbsent(head, new WordHeadIdDto(head, e.getId()));
        }

        return new ArrayList<>(map.values());
    }

    @Override
    public void scoreEnglishWord(EnglishWordScoreRequest request, Long user) {
        EnglishWord byId = getById(request.getId());
        ThrowUtils.throwIf(byId == null, ErrorCode.NOT_FOUND_ERROR);

        Integer score = request.getScore();

        byId.setManual_score(score);
        byId.setReviewer(user);
        byId.setStatus(WordStatus.WAIT_FOR_AI_REVIEW.name());

        updateById(byId);
    }

    @Override
    public WordStatusChange getEnglishWordAutoScore(Long id) {

        return statusChangeService.getOne(new QueryWrapper<WordStatusChange>().eq("word_id", id).eq("COMMENT", "AI_AUTO_RATE").last("LIMIT 1"));
    }

    @Override
    public List<DuplicateWordDto> findDuplicateWords() {
        List<Map<String, Object>> maps = getBaseMapper().selectMaps(
                new QueryWrapper<EnglishWord>()
                        .select("word_head", "COUNT(*) AS cnt")
                        .groupBy("word_head")
                        .having("COUNT(*) > 1")
        );

        return maps.stream()
                .map(m -> new DuplicateWordDto(
                        (String) m.get("word_head"),
                        ((Number) m.get("cnt")).longValue()
                ))
                .collect(Collectors.toList());
    }

}
