package com.quotawish.leaveword.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotawish.leaveword.annotation.AuthCheck;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.DeleteRequest;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.UserConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.category.CategoryQueryRequest;
import com.quotawish.leaveword.model.dto.core.ReqCreateGroup;
import com.quotawish.leaveword.model.dto.core.ReqUpdateGroup;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryAddRequest;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryQueryRequest;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryUpdateRequest;
import com.quotawish.leaveword.model.entity.Category;
import com.quotawish.leaveword.model.entity.english.EnglishDictionary;
import com.quotawish.leaveword.service.CategoryService;
import org.elasticsearch.search.suggest.completion.context.CategoryQueryContext;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
* 分类表(category)表控制层
*
* @author TalexDreamSoul
*/
@RestController
@RequestMapping("/category")
public class CategoryController {
/**
* 服务对象
*/
    @Autowired
    private CategoryService categoryService;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public Category selectOne(Integer id) {
    return categoryService.getOne(new QueryWrapper<Category>().eq("id", id));
    }

    /**
     * 创建英语词典
     *
     * @param category
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> addCategory(@RequestBody @Validated(ReqCreateGroup.class) Category category) {
        ThrowUtils.throwIf(categoryService.getById(category.getId()) != null, ErrorCode.PARAMS_ERROR);

        return ResultUtils.success(categoryService.save(category));
    }


    /**
     * 删除分类
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteEnglishDictionary(@RequestBody @Validated @NotNull(message = "请求体不能为空") DeleteRequest deleteRequest) {
        long id = deleteRequest.getId();

        Category oldCategory = categoryService.getById(id);
        ThrowUtils.throwIf(oldCategory == null, ErrorCode.NOT_FOUND_ERROR);

        boolean result = categoryService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(true);
    }

    /**
     * 更新分类
     *
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateEnglishDictionary(@RequestBody @Validated(ReqUpdateGroup.class) Category paramCategory) {
        Category category = categoryService.getById(paramCategory.getId());

        ThrowUtils.throwIf(category == null, ErrorCode.NOT_FOUND_ERROR);

        // 操作数据库
        boolean result = categoryService.updateById(category);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(true);
    }

    /**
     * 分页获取英语词典列表（仅管理员可用）
     *
     * @param categoryQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Category>> listEnglishDictionaryByPage(@RequestBody CategoryQueryRequest categoryQueryRequest) {
        long current = categoryQueryRequest.getCurrent();
        long size = categoryQueryRequest.getPageSize();
        // 查询数据库
        Page<Category> englishDictionaryPage = categoryService.page(new Page<>(current, size),
                categoryService.getQueryWrapper(categoryQueryRequest));

        return ResultUtils.success(englishDictionaryPage);
    }

}
