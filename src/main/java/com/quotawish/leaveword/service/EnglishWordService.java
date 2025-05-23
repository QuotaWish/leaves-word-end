package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.quotawish.leaveword.model.dto.english.english_word.*;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.model.vo.english.DictionaryWordWithWordVO;
import com.quotawish.leaveword.model.vo.english.EnglishWordVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 英语单词服务
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
public interface EnglishWordService extends IService<EnglishWord> {

    /**
     * 获取查询条件
     *
     * @param english_wordQueryRequest
     * @return
     */
    QueryWrapper<EnglishWord> getQueryWrapper(EnglishWordQueryRequest english_wordQueryRequest);

    /**
     * 获取查询条件（词典）
     *
     * @param request
     * @return
     */
    IPage<DictionaryWordWithWordVO> getQueryWrapper(EnglishWordQueryDictRequest request);

    /**
     * 获取英语单词封装
     *
     * @param english_word
     * @param request
     * @return
     */
    EnglishWordVO getEnglishWordVO(EnglishWord english_word, HttpServletRequest request);

    /**
     * 分页获取英语单词封装
     *
     * @param english_wordPage
     * @param request
     * @return
     */
    Page<EnglishWordVO> getEnglishWordVOPage(Page<EnglishWord> english_wordPage, HttpServletRequest request);

    /**
     * 批量导入英语单词
     *
     * 返回一个数组
     * 1.表示成功的数量
     * 2.表示数据库已有，被过滤的数量
     * 3.表示失败的数量（失败可能是数据库无法链接等异常问题）
     */
    int[] batchImportEnglishWord(EnglishWordAddBatchRequest request);

    List<WordHeadIdDto> batchGetEnglishWordId(EnglishWordGetBatchRequest request);

    /**
     * 对某个英语单词评分 同时上传AI评分
     */
    void scoreEnglishWord(EnglishWordScoreRequest request, Long user);

    /**
     * 获取某个单词的自动AI评分 （每次处理完成后都会自动刷新一个评分）
     */
    WordStatusChange getEnglishWordAutoScore(Long id);

    /**
     * 查询所有重复出现（count > 1）的 word_head 及其出现次数
     */
    List<DuplicateWordDto> findDuplicateWords();

    /**
     * 发布某个单词（只有已通过审核的单词可以发布）
     */
    boolean publishWord(Long id);
}
