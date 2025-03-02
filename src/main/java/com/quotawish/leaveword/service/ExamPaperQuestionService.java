package com.quotawish.leaveword.service;

import java.util.List;
import com.quotawish.leaveword.model.entity.ExamPaperQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamPaperQuestionService extends IService<ExamPaperQuestion>{


    int updateBatch(List<ExamPaperQuestion> list);

    int updateBatchSelective(List<ExamPaperQuestion> list);

    int batchInsert(List<ExamPaperQuestion> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaperQuestion record);

    int insertOnDuplicateUpdateSelective(ExamPaperQuestion record);

}
