package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.mapper.ExamOperationLogMapper;
import java.util.List;
import com.quotawish.leaveword.model.entity.ExamOperationLog;
import com.quotawish.leaveword.service.ExamOperationLogService;
@Service
public class ExamOperationLogImpl extends ServiceImpl<ExamOperationLogMapper, ExamOperationLog> implements ExamOperationLogService{

    @Override
    public int updateBatch(List<ExamOperationLog> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamOperationLog> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamOperationLog> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamOperationLog record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamOperationLog record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
