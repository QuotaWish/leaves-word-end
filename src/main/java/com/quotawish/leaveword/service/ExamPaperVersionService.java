package com.quotawish.leaveword.service;

import com.quotawish.leaveword.model.entity.ExamPaperVersion;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamPaperVersionService extends IService<ExamPaperVersion>{


    int updateBatch(List<ExamPaperVersion> list);

    int updateBatchSelective(List<ExamPaperVersion> list);

    int batchInsert(List<ExamPaperVersion> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaperVersion record);

    int insertOnDuplicateUpdateSelective(ExamPaperVersion record);

}
