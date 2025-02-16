package com.quotawish.leaveword.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotawish.leaveword.annotation.AuthCheck;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.DeleteRequest;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.UserConstant;
import com.quotawish.leaveword.exception.BusinessException;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.english.english_word.*;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.model.enums.WordStatus;
import com.quotawish.leaveword.model.vo.english.DictionaryWordWithWordVO;
import com.quotawish.leaveword.model.vo.english.EnglishWordVO;
import com.quotawish.leaveword.service.EnglishWordService;
import com.quotawish.leaveword.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
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
@Api(tags = "EnglishWords")
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
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addEnglishWord(@RequestBody @Validated EnglishWordAddRequest english_wordAddRequest, HttpServletRequest request) {
        EnglishWord english_word = new EnglishWord();
        BeanUtils.copyProperties(english_wordAddRequest, english_word);

        english_word.setStatus(WordStatus.CREATED.name());

        boolean result = english_wordService.save(english_word);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        long newEnglishWordId = english_word.getId();
        return ResultUtils.success(newEnglishWordId);
    }

    /**
     * 批量创建英语单词
     */
    @PostMapping("/add/batch")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<int[]> addEnglishWordBatch(@RequestBody @Validated EnglishWordAddBatchRequest batchReq, HttpServletRequest request) {

        return ResultUtils.success(english_wordService.batchImportEnglishWord(batchReq));
    }

    @PostMapping("/get/batch")
    @ApiOperation("批量获取英语单词Id")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long[]> getEnglishWordBatch(@RequestBody @Validated EnglishWordGetBatchRequest batchReq, HttpServletRequest request) {

        return ResultUtils.success(english_wordService.batchGetEnglishWordId(batchReq));
    }

    @ApiOperation("对某个英语单词评分")
    @PostMapping("/score")
    public BaseResponse<?> scoreEnglishWord(@RequestBody @Validated EnglishWordScoreRequest request, HttpServletRequest req) {
        User user = userService.getLoginUser(req);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_LOGIN_ERROR);

        english_wordService.scoreEnglishWord(request, user.getId());

        return ResultUtils.success(true);
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
    public BaseResponse<Boolean> updateEnglishWord(@RequestBody @Validated EnglishWordUpdateRequest english_wordUpdateRequest) {
        EnglishWord english_word = new EnglishWord();
        BeanUtils.copyProperties(english_wordUpdateRequest, english_word);

        long id = english_wordUpdateRequest.getId();
        EnglishWord oldEnglishWord = english_wordService.getById(id);
        ThrowUtils.throwIf(oldEnglishWord == null, ErrorCode.NOT_FOUND_ERROR);

        // 如果正在处理 或者 正在审核 不允许操作
        if ( WordStatus.getEnumByValue(oldEnglishWord.getStatus()) == WordStatus.REVIEWING) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在审核中，请稍后再试");
        }

        english_word.setStatus(english_wordUpdateRequest.isDraft() ? WordStatus.DRAFT.name() : WordStatus.SUPPLIED.name());

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

    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation("分页获取英语单词列表")
    public BaseResponse<Page<EnglishWord>> listEnglishWordByPage(@RequestBody EnglishWordQueryRequest english_wordQueryRequest) {
        long current = english_wordQueryRequest.getCurrent();
        long size = english_wordQueryRequest.getPageSize();

        Page<EnglishWord> english_wordPage = english_wordService.page(new Page<>(current, size),
                english_wordService.getQueryWrapper(english_wordQueryRequest));
        return ResultUtils.success(english_wordPage);
    }


    @PostMapping("/list/page/dict")
    @ApiOperation("分页获取指定词典英语单词列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<IPage<DictionaryWordWithWordVO>> listEnglishWordByPage(@RequestBody EnglishWordQueryDictRequest english_wordQueryRequest) {

        return ResultUtils.success(english_wordService.getQueryWrapper(english_wordQueryRequest));
    }

    /**
     * 分页获取英语单词列表（封装类）
     *
     * @param english_wordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    @ApiOperation("分页获取封装英语单词列表")
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
//        english_wordQueryRequest.setUserId(loginUser.getId());
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
     * 获取某个单词的自动AI评分 （每次处理完成后都会自动刷新一个评分）
     */
    @GetMapping("/score")
    public BaseResponse<WordStatusChange> getScoreEnglishWord(@RequestParam("id") Long id) {
        // 获取单词
        EnglishWord english_word = english_wordService.getById(id);
        ThrowUtils.throwIf(english_word == null, ErrorCode.NOT_FOUND_ERROR);

        return ResultUtils.success(english_wordService.getEnglishWordAutoScore(id));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "根据单词获取单词数据详情", notes = "非登录状态获取的数据不完整，登录后每位用户限制频繁查询。（TODO）")
    public BaseResponse<EnglishWord> getEnglishWordDetail(@RequestParam("word") String word, HttpServletRequest request) {
        EnglishWord english_word = english_wordService.getOne(new QueryWrapper<EnglishWord>().eq("word_head", word));
        ThrowUtils.throwIf(english_word == null, ErrorCode.NOT_FOUND_ERROR);

        return ResultUtils.success(english_word);
    }
}
