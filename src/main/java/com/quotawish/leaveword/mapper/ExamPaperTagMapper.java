package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.ExamPaperTag;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamPaperTagMapper extends BaseMapper<ExamPaperTag> {
    int updateBatch(@Param("list") List<ExamPaperTag> list);

    int updateBatchSelective(@Param("list") List<ExamPaperTag> list);

    int batchInsert(@Param("list") List<ExamPaperTag> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(ExamPaperTag record);

    int insertOnDuplicateUpdateSelective(ExamPaperTag record);
}