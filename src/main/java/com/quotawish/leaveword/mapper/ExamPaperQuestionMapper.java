package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamPaperQuestion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamPaperQuestionMapper extends BaseMapper<ExamPaperQuestion> {
    int updateBatch(@Param("list") List<ExamPaperQuestion> list);

    int updateBatchSelective(@Param("list") List<ExamPaperQuestion> list);

    int batchInsert(@Param("list") List<ExamPaperQuestion> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaperQuestion record);

    int insertOnDuplicateUpdateSelective(ExamPaperQuestion record);
}