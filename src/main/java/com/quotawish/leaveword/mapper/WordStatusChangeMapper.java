package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import org.apache.ibatis.annotations.Param;

public interface WordStatusChangeMapper extends BaseMapper<WordStatusChange> {
    int updateBatch(@Param("list") List<WordStatusChange> list);

    int updateBatchSelective(@Param("list") List<WordStatusChange> list);

    int batchInsert(@Param("list") List<WordStatusChange> list);

    int deleteByPrimaryKeyIn(List<Long> list);

    int insertOnDuplicateUpdate(WordStatusChange record);

    int insertOnDuplicateUpdateSelective(WordStatusChange record);
}