package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.english.english_word.change_log.EnglishWordChangeLogQueryRequest;
import com.quotawish.leaveword.model.entity.english.word.EnglishWordChangeLog;
import com.quotawish.leaveword.model.vo.english.EnglishWordChangeLogVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 英语单词变更记录服务
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
public interface EnglishWordChangeLogService extends IService<EnglishWordChangeLog> {

    /**
     * 校验数据
     *
     * @param english_word_change_log
     * @param add 对创建的数据进行校验
     */
    void validEnglishWordChangeLog(EnglishWordChangeLog english_word_change_log, boolean add);

    /**
     * 获取查询条件
     *
     * @param english_word_change_logQueryRequest
     * @return
     */
    QueryWrapper<EnglishWordChangeLog> getQueryWrapper(EnglishWordChangeLogQueryRequest english_word_change_logQueryRequest);
    
    /**
     * 获取英语单词变更记录封装
     *
     * @param english_word_change_log
     * @param request
     * @return
     */
    EnglishWordChangeLogVO getEnglishWordChangeLogVO(EnglishWordChangeLog english_word_change_log, HttpServletRequest request);

    /**
     * 分页获取英语单词变更记录封装
     *
     * @param english_word_change_logPage
     * @param request
     * @return
     */
    Page<EnglishWordChangeLogVO> getEnglishWordChangeLogVOPage(Page<EnglishWordChangeLog> english_word_change_logPage, HttpServletRequest request);
}
