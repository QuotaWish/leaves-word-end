package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamPaper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamPaperMapper extends BaseMapper<ExamPaper> {
    int updateBatch(@Param("list") List<ExamPaper> list);

    int updateBatchSelective(@Param("list") List<ExamPaper> list);

    int batchInsert(@Param("list") List<ExamPaper> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaper record);

    int insertOnDuplicateUpdateSelective(ExamPaper record);
}