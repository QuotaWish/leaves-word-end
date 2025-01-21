package com.quotawish.leaveword.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotawish.leaveword.annotation.AuthCheck;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.UserConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.english.english_word.change_log.EnglishWordChangeLogQueryRequest;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.english.word.EnglishWordChangeLog;
import com.quotawish.leaveword.model.vo.english.EnglishWordChangeLogVO;
import com.quotawish.leaveword.service.EnglishWordChangeLogService;
import com.quotawish.leaveword.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 英语单词变更记录接口
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@RestController
@RequestMapping("/english_word_change_log")
@Slf4j
public class EnglishWordChangeLogController {

    @Resource
    private EnglishWordChangeLogService english_word_change_logService;

    @Resource
    private UserService userService;

    /**
     * 根据 id 获取英语单词变更记录（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<EnglishWordChangeLogVO> getEnglishWordChangeLogVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        EnglishWordChangeLog english_word_change_log = english_word_change_logService.getById(id);
        ThrowUtils.throwIf(english_word_change_log == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(english_word_change_logService.getEnglishWordChangeLogVO(english_word_change_log, request));
    }

    /**
     * 分页获取英语单词变更记录列表（仅管理员可用）
     *
     * @param english_word_change_logQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<EnglishWordChangeLog>> listEnglishWordChangeLogByPage(@RequestBody EnglishWordChangeLogQueryRequest english_word_change_logQueryRequest) {
        long current = english_word_change_logQueryRequest.getCurrent();
        long size = english_word_change_logQueryRequest.getPageSize();
        // 查询数据库
        Page<EnglishWordChangeLog> english_word_change_logPage = english_word_change_logService.page(new Page<>(current, size),
                english_word_change_logService.getQueryWrapper(english_word_change_logQueryRequest));
        return ResultUtils.success(english_word_change_logPage);
    }

    /**
     * 分页获取英语单词变更记录列表（封装类）
     *
     * @param english_word_change_logQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<EnglishWordChangeLogVO>> listEnglishWordChangeLogVOByPage(@RequestBody EnglishWordChangeLogQueryRequest english_word_change_logQueryRequest,
                                                               HttpServletRequest request) {
        long current = english_word_change_logQueryRequest.getCurrent();
        long size = english_word_change_logQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<EnglishWordChangeLog> english_word_change_logPage = english_word_change_logService.page(new Page<>(current, size),
                english_word_change_logService.getQueryWrapper(english_word_change_logQueryRequest));
        // 获取封装类
        return ResultUtils.success(english_word_change_logService.getEnglishWordChangeLogVOPage(english_word_change_logPage, request));
    }

    /**
     * 分页获取当前登录用户创建的英语单词变更记录列表
     *
     * @param english_word_change_logQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<EnglishWordChangeLogVO>> listMyEnglishWordChangeLogVOByPage(@RequestBody EnglishWordChangeLogQueryRequest english_word_change_logQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(english_word_change_logQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        english_word_change_logQueryRequest.setUserId(loginUser.getId());
        long current = english_word_change_logQueryRequest.getCurrent();
        long size = english_word_change_logQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<EnglishWordChangeLog> english_word_change_logPage = english_word_change_logService.page(new Page<>(current, size),
                english_word_change_logService.getQueryWrapper(english_word_change_logQueryRequest));
        // 获取封装类
        return ResultUtils.success(english_word_change_logService.getEnglishWordChangeLogVOPage(english_word_change_logPage, request));
    }
}
