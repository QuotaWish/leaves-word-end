package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.Category;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CategoryMapper extends BaseMapper<Category> {
    int updateBatch(@Param("list") List<Category> list);

    int updateBatchSelective(@Param("list") List<Category> list);

    int batchInsert(@Param("list") List<Category> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(Category record);

    int insertOnDuplicateUpdateSelective(Category record);
}