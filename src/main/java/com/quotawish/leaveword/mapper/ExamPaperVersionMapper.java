package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamPaperVersion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamPaperVersionMapper extends BaseMapper<ExamPaperVersion> {
    int updateBatch(@Param("list") List<ExamPaperVersion> list);

    int updateBatchSelective(@Param("list") List<ExamPaperVersion> list);

    int batchInsert(@Param("list") List<ExamPaperVersion> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaperVersion record);

    int insertOnDuplicateUpdateSelective(ExamPaperVersion record);
}