package com.quotawish.leaveword.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.audio.AudioFileQueryRequest;
import com.quotawish.leaveword.model.dto.english.status_change.EnglishWordStatusChangeQueryRequest;
import com.quotawish.leaveword.model.entity.audio.AudioFile;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;

import java.util.List;

/**
 * @author TalexDS
 * @description 针对表【word_status_change(单词状态变更记录表)】的数据库操作Service
 * @createDate 2025-01-24 12:47:41
 */
public interface WordStatusChangeService extends IService<WordStatusChange> {

//    int updateBatch(List<WordStatusChange> list);
//
//    int updateBatchSelective(List<WordStatusChange> list);
//
//    int batchInsert(List<WordStatusChange> list);
//
//    int deleteByPrimaryKeyIn(List<Long> list);
//
//    int insertOnDuplicateUpdate(WordStatusChange record);
//
//    int insertOnDuplicateUpdateSelective(WordStatusChange record);

    /**
     * 根据某个单词获取单词状态变更记录（分页） 默认返回前10条记录 传入记录id获取更早的记录
     */
    List<WordStatusChange> selectListByWordId(Long wordId, Long lastId);

    QueryWrapper<WordStatusChange> getQueryWrapper(EnglishWordStatusChangeQueryRequest englishWordStatusChangeQueryRequest);
}

