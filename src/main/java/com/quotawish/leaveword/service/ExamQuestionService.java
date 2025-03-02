package com.quotawish.leaveword.service;

import java.util.List;
import com.quotawish.leaveword.model.entity.ExamQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamQuestionService extends IService<ExamQuestion>{


    int updateBatch(List<ExamQuestion> list);

    int updateBatchSelective(List<ExamQuestion> list);

    int batchInsert(List<ExamQuestion> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamQuestion record);

    int insertOnDuplicateUpdateSelective(ExamQuestion record);

}
