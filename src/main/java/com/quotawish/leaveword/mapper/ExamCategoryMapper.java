package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamCategory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamCategoryMapper extends BaseMapper<ExamCategory> {
    int updateBatch(@Param("list") List<ExamCategory> list);

    int updateBatchSelective(@Param("list") List<ExamCategory> list);

    int batchInsert(@Param("list") List<ExamCategory> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamCategory record);

    int insertOnDuplicateUpdateSelective(ExamCategory record);
}