package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorQueryRequest;
import com.quotawish.leaveword.model.entity.MediaCreator;
import com.quotawish.leaveword.model.vo.MediaCreatorVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 媒体创建表服务
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
public interface MediaCreatorService extends IService<MediaCreator> {

    /**
     * 校验数据
     *
     * @param media_creator
     * @param add 对创建的数据进行校验
     */
    void validMediaCreator(MediaCreator media_creator, boolean add);

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
}
