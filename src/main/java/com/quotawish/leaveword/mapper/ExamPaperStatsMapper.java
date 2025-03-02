package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamPaperStats;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamPaperStatsMapper extends BaseMapper<ExamPaperStats> {
    int updateBatch(@Param("list") List<ExamPaperStats> list);

    int updateBatchSelective(@Param("list") List<ExamPaperStats> list);

    int batchInsert(@Param("list") List<ExamPaperStats> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaperStats record);

    int insertOnDuplicateUpdateSelective(ExamPaperStats record);
}