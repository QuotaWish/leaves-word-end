package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.quotawish.leaveword.model.entity.ExamQuestion;
import com.quotawish.leaveword.mapper.ExamQuestionMapper;
import com.quotawish.leaveword.service.ExamQuestionService;
@Service
public class ExamQuestionImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion> implements ExamQuestionService{

    @Override
    public int updateBatch(List<ExamQuestion> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamQuestion> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamQuestion> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamQuestion record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamQuestion record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
