package com.quotawish.leaveword.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotawish.leaveword.annotation.AuthCheck;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.DeleteRequest;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.UserConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.english.dictionary_word.DictionaryWordAddRequest;
import com.quotawish.leaveword.model.dto.english.dictionary_word.DictionaryWordQueryRequest;
import com.quotawish.leaveword.model.dto.english.dictionary_word.EnglishWordRelativeBatchRequest;
import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.model.vo.english.DictionaryWordVO;
import com.quotawish.leaveword.service.DictionaryWordService;
import com.quotawish.leaveword.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 词典单词表接口
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@RestController
@RequestMapping("/dictionary_word")
@Slf4j
public class DictionaryWordController {

    @Resource
    private DictionaryWordService dictionary_wordService;

    @Resource
    private UserService userService;

    /**
     * 批量关联词典和单词
     */
    @PostMapping("/add/batch")
    public BaseResponse<int[]> addDictionaryWordBatch(@RequestBody @Validated EnglishWordRelativeBatchRequest request) {
        Long[] wordIds = request.getWords().stream().toArray(Long[]::new);

        int[] result = dictionary_wordService.batchRelativeDictionaryWord(request.getDictionary_id(), wordIds);

        return ResultUtils.success(result);
    }

    /**
     * 根据某个词典获取对应的关系列表
     */
    @PostMapping("/list/batch")
    public BaseResponse< List<DictionaryWord>> listDictionaryWordBatch(@RequestBody @Validated DictionaryWordQueryRequest request) {
        List<DictionaryWord> result = dictionary_wordService.listDictionaryWordBatch(request.getDictionary_id());

        return ResultUtils.success(result);
    }

    /**
     * 创建词典单词表
     *
     * @param dictionary_wordAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addDictionaryWord(@RequestBody @Validated DictionaryWordAddRequest dictionary_wordAddRequest, HttpServletRequest request) {
        DictionaryWord dictionary_word = new DictionaryWord();
        BeanUtils.copyProperties(dictionary_wordAddRequest, dictionary_word);

        boolean result = dictionary_wordService.save(dictionary_word);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        long newDictionaryWordId = dictionary_word.getId();
        return ResultUtils.success(newDictionaryWordId);
    }

    /**
     * 删除词典单词表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteDictionaryWord(@RequestBody @Validated DeleteRequest deleteRequest, HttpServletRequest request) {
        long id = deleteRequest.getId();
        // 判断是否存在
        DictionaryWord oldDictionaryWord = dictionary_wordService.getById(id);
        ThrowUtils.throwIf(oldDictionaryWord == null, ErrorCode.NOT_FOUND_ERROR);

        // 操作数据库
        boolean result = dictionary_wordService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取词典单词表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<DictionaryWordVO> getDictionaryWordVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        DictionaryWord dictionary_word = dictionary_wordService.getById(id);
        ThrowUtils.throwIf(dictionary_word == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(dictionary_wordService.getDictionaryWordVO(dictionary_word, request));
    }

    /**
     * 分页获取词典单词表列表（仅管理员可用）
     *
     * @param dictionary_wordQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<DictionaryWord>> listDictionaryWordByPage(@RequestBody DictionaryWordQueryRequest dictionary_wordQueryRequest) {
        long current = dictionary_wordQueryRequest.getCurrent();
        long size = dictionary_wordQueryRequest.getPageSize();
        // 查询数据库
        Page<DictionaryWord> dictionary_wordPage = dictionary_wordService.page(new Page<>(current, size),
                dictionary_wordService.getQueryWrapper(dictionary_wordQueryRequest));
        return ResultUtils.success(dictionary_wordPage);
    }

    /**
     * 分页获取词典单词表列表（封装类）
     *
     * @param dictionary_wordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<DictionaryWordVO>> listDictionaryWordVOByPage(@RequestBody DictionaryWordQueryRequest dictionary_wordQueryRequest,
                                                               HttpServletRequest request) {
        long current = dictionary_wordQueryRequest.getCurrent();
        long size = dictionary_wordQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<DictionaryWord> dictionary_wordPage = dictionary_wordService.page(new Page<>(current, size),
                dictionary_wordService.getQueryWrapper(dictionary_wordQueryRequest));
        // 获取封装类
        return ResultUtils.success(dictionary_wordService.getDictionaryWordVOPage(dictionary_wordPage, request));
    }
}
