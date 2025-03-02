package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.quotawish.leaveword.mapper.ExamPaperMapper;
import com.quotawish.leaveword.model.entity.ExamPaper;
import com.quotawish.leaveword.service.ExamPaperService;
@Service
public class ExamPaperImpl extends ServiceImpl<ExamPaperMapper, ExamPaper> implements ExamPaperService{

    @Override
    public int updateBatch(List<ExamPaper> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamPaper> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamPaper> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamPaper record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamPaper record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
