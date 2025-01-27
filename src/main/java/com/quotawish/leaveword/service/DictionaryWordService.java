package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.english.dictionary_word.DictionaryWordQueryRequest;
import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.model.vo.english.DictionaryWordVO;

import javax.servlet.http.HttpServletRequest;

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
}
