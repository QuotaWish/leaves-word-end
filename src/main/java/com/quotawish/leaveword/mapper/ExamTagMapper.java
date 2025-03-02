package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamTag;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamTagMapper extends BaseMapper<ExamTag> {
    int updateBatch(@Param("list") List<ExamTag> list);

    int updateBatchSelective(@Param("list") List<ExamTag> list);

    int batchInsert(@Param("list") List<ExamTag> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamTag record);

    int insertOnDuplicateUpdateSelective(ExamTag record);
}