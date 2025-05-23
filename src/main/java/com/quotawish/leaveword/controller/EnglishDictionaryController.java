package com.quotawish.leaveword.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotawish.leaveword.annotation.AuthCheck;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.DeleteRequest;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.UserConstant;
import com.quotawish.leaveword.exception.BusinessException;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryAddRequest;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryImportRequest;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryQueryRequest;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryUpdateRequest;
import com.quotawish.leaveword.model.entity.Category;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.english.EnglishDictionary;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.vo.english.EnglishDictionaryVO;
import com.quotawish.leaveword.model.vo.english.EnglishDictionaryWithCategoryVO;
import com.quotawish.leaveword.service.DictionaryCategoryService;
import com.quotawish.leaveword.service.EnglishDictionaryService;
import com.quotawish.leaveword.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 英语词典接口
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@RestController
@RequestMapping("/english_dictionary")
@Slf4j
public class EnglishDictionaryController {

    @Resource
    private EnglishDictionaryService englishDictionaryService;

    @Resource
    private UserService userService;

    @Resource
    private DictionaryCategoryService dictionaryCategoryService;

    /**
     * 给某一个英语词典批量导入单词
     * 导入的单词会自动关联到这个词典
     *
     * 返回：批量导入的单词数量，成功导入的单词列表
     *
     * 如果某个词典中已有单词会返回到 repeatWordList
     * 如果某个单词不存在数据，会返回到 notExistWordList
     */
    @PostMapping("/import")
    public BaseResponse<Boolean> importEnglishDictionary(@RequestBody @Validated @NotNull(message = "请求体不能为空") EnglishDictionaryImportRequest englishDictionaryAddRequest) {
        return ResultUtils.success(false);
    }

    /**
     * 创建英语词典
     *
     * @param englishDictionaryAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addEnglishDictionary(@RequestBody @Validated @NotNull(message = "请求体不能为空") EnglishDictionaryAddRequest englishDictionaryAddRequest, HttpServletRequest request) {
        EnglishDictionary englishDictionary = new EnglishDictionary();
        BeanUtils.copyProperties(englishDictionaryAddRequest, englishDictionary);

        // 写入数据库
        boolean result = englishDictionaryService.save(englishDictionary);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        // 返回新写入的数据 id
        long newEnglishDictionaryId = englishDictionary.getId();
        return ResultUtils.success(newEnglishDictionaryId);
    }

    /**
     * 删除英语词典
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteEnglishDictionary(@RequestBody @Validated @NotNull(message = "请求体不能为空") DeleteRequest deleteRequest, HttpServletRequest request) {
        long id = deleteRequest.getId();

        EnglishDictionary oldEnglishDictionary = englishDictionaryService.getById(id);
        ThrowUtils.throwIf(oldEnglishDictionary == null, ErrorCode.NOT_FOUND_ERROR);

        boolean result = englishDictionaryService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新英语词典（仅管理员可用）
     *
     * @param englishDictionaryUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateEnglishDictionary(@RequestBody @Validated @NotNull(message = "请求体不能为空") EnglishDictionaryUpdateRequest englishDictionaryUpdateRequest) {
        EnglishDictionary englishDictionary = new EnglishDictionary();
        BeanUtils.copyProperties(englishDictionaryUpdateRequest, englishDictionary);

        // 判断是否存在
        long id = englishDictionaryUpdateRequest.getId();
        EnglishDictionary oldEnglishDictionary = englishDictionaryService.getById(id);
        ThrowUtils.throwIf(oldEnglishDictionary == null, ErrorCode.NOT_FOUND_ERROR);

        // 操作数据库
        boolean result = englishDictionaryService.updateById(englishDictionary);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取英语词典（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<EnglishDictionaryVO> getEnglishDictionaryVOById(@RequestParam(name = "id") @Validated String query, HttpServletRequest request) {
        long id;

        try {
            id = Long.parseLong(query);
        } catch (NumberFormatException nfe) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "id 必须为正整数");
        }

        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        EnglishDictionary englishDictionary = englishDictionaryService.getById(id);
        ThrowUtils.throwIf(englishDictionary == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(englishDictionaryService.getEnglishDictionaryVO(englishDictionary, request));
    }

    /**
     * 分页获取英语词典列表（仅管理员可用）
     *
     * @param englishDictionaryQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<EnglishDictionary>> listEnglishDictionaryByPage(@RequestBody EnglishDictionaryQueryRequest englishDictionaryQueryRequest) {
        long current = englishDictionaryQueryRequest.getCurrent();
        long size = englishDictionaryQueryRequest.getPageSize();
        // 查询数据库
        Page<EnglishDictionary> englishDictionaryPage = englishDictionaryService.page(new Page<>(current, size),
                englishDictionaryService.getQueryWrapper(englishDictionaryQueryRequest));
        return ResultUtils.success(englishDictionaryPage);
    }

    /**
     * 分页获取英语词典列表（封装类）
     *
     * @param englishDictionaryQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<EnglishDictionaryVO>> listEnglishDictionaryVOByPage(@RequestBody EnglishDictionaryQueryRequest englishDictionaryQueryRequest,
                                                                   HttpServletRequest request) {
        long current = englishDictionaryQueryRequest.getCurrent();
        long size = englishDictionaryQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<EnglishDictionary> englishDictionaryPage = englishDictionaryService.page(new Page<>(current, size),
                englishDictionaryService.getQueryWrapper(englishDictionaryQueryRequest));
        // 获取封装类
        return ResultUtils.success(englishDictionaryService.getEnglishDictionaryVOPage(englishDictionaryPage, request));
    }

    /**
     * 分页获取当前登录用户创建的英语词典列表
     *
     * @param englishDictionaryQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<EnglishDictionaryVO>> listMyEnglishDictionaryVOByPage(@RequestBody EnglishDictionaryQueryRequest englishDictionaryQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(englishDictionaryQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        englishDictionaryQueryRequest.setUserId(loginUser.getId());
        long current = englishDictionaryQueryRequest.getCurrent();
        long size = englishDictionaryQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<EnglishDictionary> englishDictionaryPage = englishDictionaryService.page(new Page<>(current, size),
                englishDictionaryService.getQueryWrapper(englishDictionaryQueryRequest));
        // 获取封装类
        return ResultUtils.success(englishDictionaryService.getEnglishDictionaryVOPage(englishDictionaryPage, request));
    }

    /**
     * 用户获取词典列表
     */
    @GetMapping("/list")
    public BaseResponse<List<EnglishDictionaryWithCategoryVO>> listEnglishDictionary() {
        List<EnglishDictionary> englishDictionaryList = englishDictionaryService.list(new QueryWrapper<>());
        List<EnglishDictionaryWithCategoryVO> englishDictionaryWithCategoryVOList = englishDictionaryList.stream().map(englishDictionary -> {
            Collection<Category> dictionaryCategoryByDictionaryId = dictionaryCategoryService.getDictionaryCategoryByDictionaryId(englishDictionary.getId());

            return EnglishDictionaryWithCategoryVO.objToVo(englishDictionary, new ArrayList<>(dictionaryCategoryByDictionaryId));
        }).collect(Collectors.toList());

        return ResultUtils.success(englishDictionaryWithCategoryVOList);
    }
}
