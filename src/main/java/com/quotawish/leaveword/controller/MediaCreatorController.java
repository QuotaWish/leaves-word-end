package com.quotawish.leaveword.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotawish.leaveword.annotation.AuthCheck;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.DeleteRequest;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.UserConstant;
import com.quotawish.leaveword.exception.BusinessException;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorAddRequest;
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorQueryRequest;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.audio.MediaCreator;
import com.quotawish.leaveword.model.enums.MediaType;
import com.quotawish.leaveword.model.vo.MediaCreatorVO;
import com.quotawish.leaveword.service.MediaCreatorService;
import com.quotawish.leaveword.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 媒体创建表接口
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@RestController
@RequestMapping("/media_creator")
@Slf4j
public class MediaCreatorController {

    @Resource
    private MediaCreatorService media_creatorService;

    @Resource
    private UserService userService;

    /**
     * 创建媒体创建表
     *
     * @param media_creatorAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public SseEmitter addMediaCreator(@Valid @RequestBody @Validated MediaCreatorAddRequest media_creatorAddRequest, HttpServletRequest request) {

        User loginUser = userService.getLoginUser(request);

        return this.media_creatorService.startWorkFlow(media_creatorAddRequest.getMediaType(), media_creatorAddRequest.getWordId(), loginUser.getId());
    }

    /**
     * 删除媒体创建表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteMediaCreator(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
//        if (deleteRequest == null || deleteRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User user = userService.getLoginUser(request);
//        long id = deleteRequest.getId();
//        // 判断是否存在
//        MediaCreator oldMediaCreator = media_creatorService.getById(id);
//        ThrowUtils.throwIf(oldMediaCreator == null, ErrorCode.NOT_FOUND_ERROR);
//        // 仅本人或管理员可删除
//        if (!oldMediaCreator.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
//        // 操作数据库
//        boolean result = media_creatorService.removeById(id);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(false);
    }


    /**
     * 根据 id 获取媒体创建表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<MediaCreatorVO> getMediaCreatorVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        MediaCreator media_creator = media_creatorService.getById(id);
        ThrowUtils.throwIf(media_creator == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(media_creatorService.getMediaCreatorVO(media_creator, request));
    }

    /**
     * 分页获取媒体创建表列表（仅管理员可用）
     *
     * @param media_creatorQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<MediaCreator>> listMediaCreatorByPage(@RequestBody MediaCreatorQueryRequest media_creatorQueryRequest) {
        long current = media_creatorQueryRequest.getCurrent();
        long size = media_creatorQueryRequest.getPageSize();
        // 查询数据库
        Page<MediaCreator> media_creatorPage = media_creatorService.page(new Page<>(current, size),
                media_creatorService.getQueryWrapper(media_creatorQueryRequest));
        return ResultUtils.success(media_creatorPage);
    }

    /**
     * 分页获取媒体创建表列表（封装类）
     *
     * @param media_creatorQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<MediaCreatorVO>> listMediaCreatorVOByPage(@RequestBody MediaCreatorQueryRequest media_creatorQueryRequest,
                                                               HttpServletRequest request) {
        long current = media_creatorQueryRequest.getCurrent();
        long size = media_creatorQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<MediaCreator> media_creatorPage = media_creatorService.page(new Page<>(current, size),
                media_creatorService.getQueryWrapper(media_creatorQueryRequest));
        // 获取封装类
        return ResultUtils.success(media_creatorService.getMediaCreatorVOPage(media_creatorPage, request));
    }

    /**
     * 分页获取当前登录用户创建的媒体创建表列表
     *
     * @param media_creatorQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<MediaCreatorVO>> listMyMediaCreatorVOByPage(@RequestBody MediaCreatorQueryRequest media_creatorQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(media_creatorQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        media_creatorQueryRequest.setUserId(loginUser.getId());
        long current = media_creatorQueryRequest.getCurrent();
        long size = media_creatorQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<MediaCreator> media_creatorPage = media_creatorService.page(new Page<>(current, size),
                media_creatorService.getQueryWrapper(media_creatorQueryRequest));
        // 获取封装类
        return ResultUtils.success(media_creatorService.getMediaCreatorVOPage(media_creatorPage, request));
    }
}
