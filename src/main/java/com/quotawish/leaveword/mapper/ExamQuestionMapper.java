package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamQuestion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {
    int updateBatch(@Param("list") List<ExamQuestion> list);

    int updateBatchSelective(@Param("list") List<ExamQuestion> list);

    int batchInsert(@Param("list") List<ExamQuestion> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamQuestion record);

    int insertOnDuplicateUpdateSelective(ExamQuestion record);
}