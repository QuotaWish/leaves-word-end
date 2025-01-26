package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.english.english_word.EnglishWordQueryRequest;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.vo.english.EnglishWordVO;

import javax.servlet.http.HttpServletRequest;

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
}
