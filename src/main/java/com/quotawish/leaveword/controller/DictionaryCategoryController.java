package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.model.dto.category.CategoryRelativeRequest;
import com.quotawish.leaveword.model.entity.Category;
import com.quotawish.leaveword.model.entity.DictionaryCategory;
import com.quotawish.leaveword.service.DictionaryCategoryService;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* 书籍分类关系表(dictionary_category)表控制层
*
* @author TalexDreamSoul
*/
@RestController
@RequestMapping("/dictionary_category")
public class DictionaryCategoryController {
/**
* 服务对象
*/
    @Autowired
    private DictionaryCategoryService dictionaryCategoryService;

    /**
     * 给某本书分配分类
     *
     * 多对多 一本书可以有多个分类
     */
    @PostMapping("/relative")
    public BaseResponse<Boolean> relativeDictionaryCategory(@RequestBody @Validated CategoryRelativeRequest request) {
        return ResultUtils.success(dictionaryCategoryService.addCategoryForDictionaryWithBatch(request));
    }

    /**
     * 获取某本书的关联分类
     */
    @GetMapping("/relative")
    public BaseResponse<List<Category>> getDictionaryCategoryByDictionaryId(@RequestParam("dict_id") Long dict_id) {
        return ResultUtils.success(dictionaryCategoryService.getDictionaryCategoryByDictionaryId(dict_id));
    }

}
