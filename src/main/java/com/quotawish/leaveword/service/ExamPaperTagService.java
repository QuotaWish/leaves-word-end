package com.quotawish.leaveword.service;

import com.quotawish.leaveword.model.entity.ExamPaperTag;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamPaperTagService extends IService<ExamPaperTag>{


    int updateBatch(List<ExamPaperTag> list);

    int updateBatchSelective(List<ExamPaperTag> list);

    int batchInsert(List<ExamPaperTag> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaperTag record);

    int insertOnDuplicateUpdateSelective(ExamPaperTag record);

}
