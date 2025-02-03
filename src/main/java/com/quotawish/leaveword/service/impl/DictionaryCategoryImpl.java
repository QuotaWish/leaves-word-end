package com.quotawish.leaveword.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.category.CategoryRelativeRequest;
import com.quotawish.leaveword.model.entity.Category;
import com.quotawish.leaveword.model.entity.english.EnglishDictionary;
import com.quotawish.leaveword.service.CategoryService;
import com.quotawish.leaveword.service.EnglishDictionaryService;
import com.quotawish.leaveword.service.UserService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.model.entity.DictionaryCategory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.quotawish.leaveword.mapper.DictionaryCategoryMapper;
import com.quotawish.leaveword.service.DictionaryCategoryService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class DictionaryCategoryImpl extends ServiceImpl<DictionaryCategoryMapper, DictionaryCategory> implements DictionaryCategoryService{

    @Resource
    private EnglishDictionaryService dictService;

    @Resource
    private CategoryService categoryService;

    @Override
    public int updateBatch(List<DictionaryCategory> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<DictionaryCategory> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<DictionaryCategory> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(DictionaryCategory record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(DictionaryCategory record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }

    @Override
    @Transactional()
    public boolean addCategoryForDictionaryWithBatch(CategoryRelativeRequest req) {
        EnglishDictionary byId = dictService.getById(req.getDict_id());
        ThrowUtils.throwIf(byId == null, ErrorCode.NOT_FOUND_ERROR);

        List<DictionaryCategory> relatives = new ArrayList<>();

        for (int category_id : req.getCategory_ids()) {
            DictionaryCategory dictionaryCategory = new DictionaryCategory();
            dictionaryCategory.setDictionaryId(req.getDict_id());
            dictionaryCategory.setCategoryId(category_id);
            dictionaryCategory.setSortOrder(1);
            relatives.add(dictionaryCategory);
        }

        return saveBatch(relatives);
    }

    @Override
    public List<Category> getDictionaryCategoryByDictionaryId(Long dict_id) {
        // 根据 dict_id 获取所有的分类
        QueryWrapper<DictionaryCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dictionary_id", dict_id);
        List<DictionaryCategory> dictionaryCategories = list(queryWrapper);

        if (dictionaryCategories.isEmpty()) {
            return new ArrayList<>();
        }

        Stream<Integer> integerStream = dictionaryCategories.stream().map(DictionaryCategory::getCategoryId);
        List<Integer> categoryIds = integerStream.collect(Collectors.toList());

        // 根据对应的category去查Category
        return categoryService.listByIds(categoryIds);
    }
}
