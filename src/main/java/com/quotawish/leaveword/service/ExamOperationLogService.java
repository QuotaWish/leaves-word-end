package com.quotawish.leaveword.service;

import java.util.List;
import com.quotawish.leaveword.model.entity.ExamOperationLog;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamOperationLogService extends IService<ExamOperationLog>{


    int updateBatch(List<ExamOperationLog> list);

    int updateBatchSelective(List<ExamOperationLog> list);

    int batchInsert(List<ExamOperationLog> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamOperationLog record);

    int insertOnDuplicateUpdateSelective(ExamOperationLog record);

}
