package com.quotawish.leaveword.service;

import java.util.List;
import com.quotawish.leaveword.model.entity.ExamPaper;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamPaperService extends IService<ExamPaper>{


    int updateBatch(List<ExamPaper> list);

    int updateBatchSelective(List<ExamPaper> list);

    int batchInsert(List<ExamPaper> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaper record);

    int insertOnDuplicateUpdateSelective(ExamPaper record);

}
