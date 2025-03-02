package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.model.entity.ExamPaperVersion;
import java.util.List;
import com.quotawish.leaveword.mapper.ExamPaperVersionMapper;
import com.quotawish.leaveword.service.ExamPaperVersionService;
@Service
public class ExamPaperVersionImpl extends ServiceImpl<ExamPaperVersionMapper, ExamPaperVersion> implements ExamPaperVersionService{

    @Override
    public int updateBatch(List<ExamPaperVersion> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamPaperVersion> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamPaperVersion> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamPaperVersion record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamPaperVersion record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
