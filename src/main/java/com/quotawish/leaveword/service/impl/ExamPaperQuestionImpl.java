package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.quotawish.leaveword.model.entity.ExamPaperQuestion;
import com.quotawish.leaveword.mapper.ExamPaperQuestionMapper;
import com.quotawish.leaveword.service.ExamPaperQuestionService;
@Service
public class ExamPaperQuestionImpl extends ServiceImpl<ExamPaperQuestionMapper, ExamPaperQuestion> implements ExamPaperQuestionService{

    @Override
    public int updateBatch(List<ExamPaperQuestion> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamPaperQuestion> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamPaperQuestion> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamPaperQuestion record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamPaperQuestion record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
