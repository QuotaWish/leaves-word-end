package com.quotawish.leaveword.service;

import com.quotawish.leaveword.model.entity.ExamCategory;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ExamCategoryService extends IService<ExamCategory>{


    int updateBatch(List<ExamCategory> list);

    int updateBatchSelective(List<ExamCategory> list);

    int batchInsert(List<ExamCategory> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamCategory record);

    int insertOnDuplicateUpdateSelective(ExamCategory record);

}
