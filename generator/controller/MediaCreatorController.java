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
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorEditRequest;
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorQueryRequest;
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorUpdateRequest;
import com.quotawish.leaveword.model.entity.MediaCreator;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.vo.MediaCreatorVO;
import com.quotawish.leaveword.service.MediaCreatorService;
import com.quotawish.leaveword.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    // region 增删改查

    /**
     * 创建媒体创建表
     *
     * @param media_creatorAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addMediaCreator(@RequestBody MediaCreatorAddRequest media_creatorAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(media_creatorAddRequest == null, ErrorCode.PARAMS_ERROR);
        // todo 在此处将实体类和 DTO 进行转换
        MediaCreator media_creator = new MediaCreator();
        BeanUtils.copyProperties(media_creatorAddRequest, media_creator);
        // 数据校验
        media_creatorService.validMediaCreator(media_creator, true);
        // todo 填充默认值
        User loginUser = userService.getLoginUser(request);
        media_creator.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = media_creatorService.save(media_creator);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newMediaCreatorId = media_creator.getId();
        return ResultUtils.success(newMediaCreatorId);
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
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        MediaCreator oldMediaCreator = media_creatorService.getById(id);
        ThrowUtils.throwIf(oldMediaCreator == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldMediaCreator.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = media_creatorService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新媒体创建表（仅管理员可用）
     *
     * @param media_creatorUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateMediaCreator(@RequestBody MediaCreatorUpdateRequest media_creatorUpdateRequest) {
        if (media_creatorUpdateRequest == null || media_creatorUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        MediaCreator media_creator = new MediaCreator();
        BeanUtils.copyProperties(media_creatorUpdateRequest, media_creator);
        // 数据校验
        media_creatorService.validMediaCreator(media_creator, false);
        // 判断是否存在
        long id = media_creatorUpdateRequest.getId();
        MediaCreator oldMediaCreator = media_creatorService.getById(id);
        ThrowUtils.throwIf(oldMediaCreator == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = media_creatorService.updateById(media_creator);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
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

    /**
     * 编辑媒体创建表（给用户使用）
     *
     * @param media_creatorEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editMediaCreator(@RequestBody MediaCreatorEditRequest media_creatorEditRequest, HttpServletRequest request) {
        if (media_creatorEditRequest == null || media_creatorEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        MediaCreator media_creator = new MediaCreator();
        BeanUtils.copyProperties(media_creatorEditRequest, media_creator);
        // 数据校验
        media_creatorService.validMediaCreator(media_creator, false);
        User loginUser = userService.getLoginUser(request);
        // 判断是否存在
        long id = media_creatorEditRequest.getId();
        MediaCreator oldMediaCreator = media_creatorService.getById(id);
        ThrowUtils.throwIf(oldMediaCreator == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldMediaCreator.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = media_creatorService.updateById(media_creator);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
