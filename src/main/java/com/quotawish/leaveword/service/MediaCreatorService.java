package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorQueryRequest;
import com.quotawish.leaveword.model.entity.audio.MediaCreator;
import com.quotawish.leaveword.model.enums.MediaType;
import com.quotawish.leaveword.model.vo.MediaCreatorVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

/**
 * 媒体创建表服务
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
public interface MediaCreatorService extends IService<MediaCreator> {

    /**
     * 获取查询条件
     *
     * @param media_creatorQueryRequest
     * @return
     */
    QueryWrapper<MediaCreator> getQueryWrapper(MediaCreatorQueryRequest media_creatorQueryRequest);
    
    /**
     * 获取媒体创建表封装
     *
     * @param media_creator
     * @param request
     * @return
     */
    MediaCreatorVO getMediaCreatorVO(MediaCreator media_creator, HttpServletRequest request);

    /**
     * 分页获取媒体创建表封装
     *
     * @param media_creatorPage
     * @param request
     * @return
     */
    Page<MediaCreatorVO> getMediaCreatorVOPage(Page<MediaCreator> media_creatorPage, HttpServletRequest request);

    /**
     * 传入媒体类型和单词ID 启动合成工作流
     */
    SseEmitter startWorkFlow(MediaType media_type, Long word_id, Long creator);
}
