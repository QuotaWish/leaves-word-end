package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryQueryRequest;
import com.quotawish.leaveword.model.entity.english.EnglishDictionary;
import com.quotawish.leaveword.model.vo.english.EnglishDictionaryVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 英语词典服务
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
public interface EnglishDictionaryService extends IService<EnglishDictionary> {

    /**
     * 获取查询条件
     *
     * @param english_dictionaryQueryRequest
     * @return
     */
    QueryWrapper<EnglishDictionary> getQueryWrapper(EnglishDictionaryQueryRequest english_dictionaryQueryRequest);
    
    /**
     * 获取英语词典封装
     *
     * @param english_dictionary
     * @param request
     * @return
     */
    EnglishDictionaryVO getEnglishDictionaryVO(EnglishDictionary english_dictionary, HttpServletRequest request);

    /**
     * 分页获取英语词典封装
     *
     * @param english_dictionaryPage
     * @param request
     * @return
     */
    Page<EnglishDictionaryVO> getEnglishDictionaryVOPage(Page<EnglishDictionary> english_dictionaryPage, HttpServletRequest request);
}
