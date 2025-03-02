package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamOperationLog;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamOperationLogMapper extends BaseMapper<ExamOperationLog> {
    int updateBatch(@Param("list") List<ExamOperationLog> list);

    int updateBatchSelective(@Param("list") List<ExamOperationLog> list);

    int batchInsert(@Param("list") List<ExamOperationLog> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamOperationLog record);

    int insertOnDuplicateUpdateSelective(ExamOperationLog record);
}