package com.quotawish.leaveword.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.model.dto.category.CategoryQueryRequest;
import com.quotawish.leaveword.model.dto.english.dictionary_word.DictionaryWordQueryRequest;
import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.utils.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.model.entity.Category;
import java.util.List;
import com.quotawish.leaveword.mapper.CategoryMapper;
import com.quotawish.leaveword.service.CategoryService;
@Service
public class CategoryImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Override
    public int updateBatch(List<Category> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<Category> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<Category> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(Category record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(Category record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }

    @Override
    public QueryWrapper<Category> getQueryWrapper(CategoryQueryRequest categoryQueryRequest) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (categoryQueryRequest == null) {
            return queryWrapper;
        }

        Long id = categoryQueryRequest.getId();
        Long notId = categoryQueryRequest.getNotId();

        String sortField = categoryQueryRequest.getSortField();
        String sortOrder = categoryQueryRequest.getSortOrder();

        String description = categoryQueryRequest.getDescription();
        String name = categoryQueryRequest.getName();

        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(name), "name", name);
        queryWrapper.eq(ObjectUtils.isNotEmpty(description), "description", description);

        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}
