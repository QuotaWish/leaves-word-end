package com.quotawish.leaveword.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.model.dto.english.status_change.EnglishWordStatusChangeQueryRequest;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.service.WordStatusChangeService;
import com.quotawish.leaveword.mapper.WordStatusChangeMapper;
import com.quotawish.leaveword.utils.SqlUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author TalexDS
* @description 针对表【word_status_change(单词状态变更记录表)】的数据库操作Service实现
* @createDate 2025-01-24 12:47:41
*/
@Service
public class WordStatusChangeServiceImpl extends ServiceImpl<WordStatusChangeMapper, WordStatusChange>
    implements WordStatusChangeService{
    @Override
    public List<WordStatusChange> selectListByWordId(Long wordId, Long lastId) {
        // 如果lastId是-1 那么就获取最新的10条 否则就从lastId往后获取
        QueryWrapper<WordStatusChange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("word_id", wordId);
        if (lastId != -1) {
            queryWrapper.gt("id", lastId);
        }
        // 优先按照update_time排序 然后是create_time
        queryWrapper.orderByDesc("update_time").orderByDesc("create_time").last("LIMIT 10");

        return list(queryWrapper);
    }

    /**
     * 获取查询条件
     *
     * @param englishWordStatusChangeQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<WordStatusChange> getQueryWrapper(EnglishWordStatusChangeQueryRequest englishWordStatusChangeQueryRequest) {
        QueryWrapper<WordStatusChange> queryWrapper = new QueryWrapper<>();
        if (englishWordStatusChangeQueryRequest == null) {
            return queryWrapper;
        }

        Long id = englishWordStatusChangeQueryRequest.getId();
        String comment = englishWordStatusChangeQueryRequest.getComment();

        String sortField = englishWordStatusChangeQueryRequest.getSortField();
        String sortOrder = englishWordStatusChangeQueryRequest.getSortOrder();

        // 从多字段中搜索
        if (StringUtils.isNotBlank(comment)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("comment", comment));
        }

        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        return queryWrapper;
    }


//    @Override
//    public int updateBatch(List<WordStatusChange> list) {
//        return baseMapper.updateBatch(list);
//    }
//    @Override
//    public int updateBatchSelective(List<WordStatusChange> list) {
//        return baseMapper.updateBatchSelective(list);
//    }
//    @Override
//    public int batchInsert(List<WordStatusChange> list) {
//        return baseMapper.batchInsert(list);
//    }
//    @Override
//    public int deleteByPrimaryKeyIn(List<Long> list) {
//        return baseMapper.deleteByPrimaryKeyIn(list);
//    }
//    @Override
//    public int insertOnDuplicateUpdate(WordStatusChange record) {
//        return baseMapper.insertOnDuplicateUpdate(record);
//    }
//    @Override
//    public int insertOnDuplicateUpdateSelective(WordStatusChange record) {
//        return baseMapper.insertOnDuplicateUpdateSelective(record);
//    }

}