package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamCategoryPaper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamCategoryPaperMapper extends BaseMapper<ExamCategoryPaper> {
    int updateBatch(@Param("list") List<ExamCategoryPaper> list);

    int updateBatchSelective(@Param("list") List<ExamCategoryPaper> list);

    int batchInsert(@Param("list") List<ExamCategoryPaper> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamCategoryPaper record);

    int insertOnDuplicateUpdateSelective(ExamCategoryPaper record);
}