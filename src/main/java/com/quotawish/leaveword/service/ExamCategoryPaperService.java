package com.quotawish.leaveword.service;

import java.util.List;
import com.quotawish.leaveword.model.entity.ExamCategoryPaper;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamCategoryPaperService extends IService<ExamCategoryPaper>{


    int updateBatch(List<ExamCategoryPaper> list);

    int updateBatchSelective(List<ExamCategoryPaper> list);

    int batchInsert(List<ExamCategoryPaper> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamCategoryPaper record);

    int insertOnDuplicateUpdateSelective(ExamCategoryPaper record);

}
