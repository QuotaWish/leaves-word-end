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
import com.quotawish.leaveword.model.dto.english.english_word.EnglishWordAddRequest;
import com.quotawish.leaveword.model.dto.english.english_word.EnglishWordEditRequest;
import com.quotawish.leaveword.model.dto.english.english_word.EnglishWordQueryRequest;
import com.quotawish.leaveword.model.dto.english.english_word.EnglishWordUpdateRequest;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.vo.english.EnglishWordVO;
import com.quotawish.leaveword.service.EnglishWordService;
import com.quotawish.leaveword.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 英语单词接口
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@RestController
@RequestMapping("/english_word")
@Slf4j
public class EnglishWordController {

    @Resource
    private EnglishWordService english_wordService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建英语单词
     *
     * @param english_wordAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addEnglishWord(@RequestBody EnglishWordAddRequest english_wordAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(english_wordAddRequest == null, ErrorCode.PARAMS_ERROR);
        // todo 在此处将实体类和 DTO 进行转换
        EnglishWord english_word = new EnglishWord();
        BeanUtils.copyProperties(english_wordAddRequest, english_word);
        // 数据校验
        english_wordService.validEnglishWord(english_word, true);
        // todo 填充默认值
//        User loginUser = userService.getLoginUser(request);
//        english_word.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = english_wordService.save(english_word);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newEnglishWordId = english_word.getId();
        return ResultUtils.success(newEnglishWordId);
    }

    /**
     * 删除英语单词
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteEnglishWord(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        EnglishWord oldEnglishWord = english_wordService.getById(id);
        ThrowUtils.throwIf(oldEnglishWord == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = english_wordService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新英语单词（仅管理员可用）
     *
     * @param english_wordUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateEnglishWord(@RequestBody EnglishWordUpdateRequest english_wordUpdateRequest) {
        if (english_wordUpdateRequest == null || english_wordUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        EnglishWord english_word = new EnglishWord();
        BeanUtils.copyProperties(english_wordUpdateRequest, english_word);
        // 数据校验
        english_wordService.validEnglishWord(english_word, false);
        // 判断是否存在
        long id = english_wordUpdateRequest.getId();
        EnglishWord oldEnglishWord = english_wordService.getById(id);
        ThrowUtils.throwIf(oldEnglishWord == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = english_wordService.updateById(english_word);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取英语单词（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<EnglishWordVO> getEnglishWordVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        EnglishWord english_word = english_wordService.getById(id);
        ThrowUtils.throwIf(english_word == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(english_wordService.getEnglishWordVO(english_word, request));
    }

    /**
     * 分页获取英语单词列表（仅管理员可用）
     *
     * @param english_wordQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<EnglishWord>> listEnglishWordByPage(@RequestBody EnglishWordQueryRequest english_wordQueryRequest) {
        long current = english_wordQueryRequest.getCurrent();
        long size = english_wordQueryRequest.getPageSize();
        // 查询数据库
        Page<EnglishWord> english_wordPage = english_wordService.page(new Page<>(current, size),
                english_wordService.getQueryWrapper(english_wordQueryRequest));
        return ResultUtils.success(english_wordPage);
    }

    /**
     * 分页获取英语单词列表（封装类）
     *
     * @param english_wordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<EnglishWordVO>> listEnglishWordVOByPage(@RequestBody EnglishWordQueryRequest english_wordQueryRequest,
                                                               HttpServletRequest request) {
        long current = english_wordQueryRequest.getCurrent();
        long size = english_wordQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<EnglishWord> english_wordPage = english_wordService.page(new Page<>(current, size),
                english_wordService.getQueryWrapper(english_wordQueryRequest));
        // 获取封装类
        return ResultUtils.success(english_wordService.getEnglishWordVOPage(english_wordPage, request));
    }

    /**
     * 分页获取当前登录用户创建的英语单词列表
     *
     * @param english_wordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<EnglishWordVO>> listMyEnglishWordVOByPage(@RequestBody EnglishWordQueryRequest english_wordQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(english_wordQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        english_wordQueryRequest.setUserId(loginUser.getId());
        long current = english_wordQueryRequest.getCurrent();
        long size = english_wordQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<EnglishWord> english_wordPage = english_wordService.page(new Page<>(current, size),
                english_wordService.getQueryWrapper(english_wordQueryRequest));
        // 获取封装类
        return ResultUtils.success(english_wordService.getEnglishWordVOPage(english_wordPage, request));
    }

    /**
     * 编辑英语单词（给用户使用）
     *
     * @param english_wordEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editEnglishWord(@RequestBody EnglishWordEditRequest english_wordEditRequest, HttpServletRequest request) {
        if (english_wordEditRequest == null || english_wordEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        EnglishWord english_word = new EnglishWord();
        BeanUtils.copyProperties(english_wordEditRequest, english_word);
        // 数据校验
        english_wordService.validEnglishWord(english_word, false);
        User loginUser = userService.getLoginUser(request);
        // 判断是否存在
        long id = english_wordEditRequest.getId();
        EnglishWord oldEnglishWord = english_wordService.getById(id);
        ThrowUtils.throwIf(oldEnglishWord == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = english_wordService.updateById(english_word);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
