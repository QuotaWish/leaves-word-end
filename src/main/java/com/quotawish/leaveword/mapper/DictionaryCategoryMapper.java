package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.DictionaryCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DictionaryCategoryMapper extends BaseMapper<DictionaryCategory> {
    int updateBatch(@Param("list") List<DictionaryCategory> list);

    int updateBatchSelective(@Param("list") List<DictionaryCategory> list);

    int batchInsert(@Param("list") List<DictionaryCategory> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(DictionaryCategory record);

    int insertOnDuplicateUpdateSelective(DictionaryCategory record);

    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(DictionaryCategory record);

    int insertOrUpdate(DictionaryCategory record);

    int insertOrUpdateSelective(DictionaryCategory record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(DictionaryCategory record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    DictionaryCategory selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(DictionaryCategory record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(DictionaryCategory record);
}