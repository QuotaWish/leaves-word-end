package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.quotawish.leaveword.model.entity.ExamPaperStats;
import com.quotawish.leaveword.mapper.ExamPaperStatsMapper;
import com.quotawish.leaveword.service.ExamPaperStatsService;
@Service
public class ExamPaperStatsImpl extends ServiceImpl<ExamPaperStatsMapper, ExamPaperStats> implements ExamPaperStatsService{

    @Override
    public int updateBatch(List<ExamPaperStats> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamPaperStats> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamPaperStats> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamPaperStats record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamPaperStats record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
