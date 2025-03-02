package com.quotawish.leaveword.service;

import com.quotawish.leaveword.model.entity.ExamTag;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamTagService extends IService<ExamTag>{


    int updateBatch(List<ExamTag> list);

    int updateBatchSelective(List<ExamTag> list);

    int batchInsert(List<ExamTag> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamTag record);

    int insertOnDuplicateUpdateSelective(ExamTag record);

}
