package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotawish.leaveword.model.dto.category.CategoryQueryRequest;
import com.quotawish.leaveword.model.dto.english.dictionary_word.DictionaryWordQueryRequest;
import com.quotawish.leaveword.model.entity.Category;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.entity.DictionaryWord;

public interface CategoryService extends IService<Category>{


    int updateBatch(List<Category> list);

    int updateBatchSelective(List<Category> list);

    int batchInsert(List<Category> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(Category record);

    int insertOnDuplicateUpdateSelective(Category record);

    /**
     * 获取查询条件
     *
     * @param CategoryQueryRequest
     * @return
     */
    QueryWrapper<Category> getQueryWrapper(CategoryQueryRequest categoryQueryRequest);

}
