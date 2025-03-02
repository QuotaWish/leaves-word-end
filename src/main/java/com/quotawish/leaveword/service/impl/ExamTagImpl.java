package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.model.entity.ExamTag;
import java.util.List;
import com.quotawish.leaveword.mapper.ExamTagMapper;
import com.quotawish.leaveword.service.ExamTagService;
@Service
public class ExamTagImpl extends ServiceImpl<ExamTagMapper, ExamTag> implements ExamTagService{

    @Override
    public int updateBatch(List<ExamTag> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamTag> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamTag> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamTag record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamTag record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
