package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.model.entity.ExamPaperTag;
import java.util.List;
import com.quotawish.leaveword.mapper.ExamPaperTagMapper;
import com.quotawish.leaveword.service.ExamPaperTagService;
@Service
public class ExamPaperTagImpl extends ServiceImpl<ExamPaperTagMapper, ExamPaperTag> implements ExamPaperTagService{

    @Override
    public int updateBatch(List<ExamPaperTag> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamPaperTag> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamPaperTag> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamPaperTag record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamPaperTag record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
