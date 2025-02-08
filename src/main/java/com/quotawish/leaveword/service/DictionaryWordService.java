package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.english.dictionary_word.DictionaryWordQueryRequest;
import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.model.vo.english.DictionaryWordVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 词典单词表服务
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
public interface DictionaryWordService extends IService<DictionaryWord> {

    /**
     * 获取查询条件
     *
     * @param dictionary_wordQueryRequest
     * @return
     */
    QueryWrapper<DictionaryWord> getQueryWrapper(DictionaryWordQueryRequest dictionary_wordQueryRequest);
    
    /**
     * 获取词典单词表封装
     *
     * @param dictionary_word
     * @param request
     * @return
     */
    DictionaryWordVO getDictionaryWordVO(DictionaryWord dictionary_word, HttpServletRequest request);

    /**
     * 分页获取词典单词表封装
     *
     * @param dictionary_wordPage
     * @param request
     * @return
     */
    Page<DictionaryWordVO> getDictionaryWordVOPage(Page<DictionaryWord> dictionary_wordPage, HttpServletRequest request);

    /**
     * 批量关联词典和单词
     */
    int[] batchRelativeDictionaryWord(Long dictionary_id, Long[] words);

    /**
     * 根据某个词典获取词典单词列表
     * 注意，本接口是一次性返回所有数据 所以有限流 TODO: limit
     */
    List<DictionaryWord> listDictionaryWordBatch(Long dictionary_id);
}
