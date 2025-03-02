package com.quotawish.leaveword.service;

import java.util.List;
import com.quotawish.leaveword.model.entity.ExamPaperStats;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamPaperStatsService extends IService<ExamPaperStats>{


    int updateBatch(List<ExamPaperStats> list);

    int updateBatchSelective(List<ExamPaperStats> list);

    int batchInsert(List<ExamPaperStats> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaperStats record);

    int insertOnDuplicateUpdateSelective(ExamPaperStats record);

}
