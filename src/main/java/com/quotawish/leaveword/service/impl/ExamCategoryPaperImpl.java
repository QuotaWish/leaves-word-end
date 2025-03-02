package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.mapper.ExamCategoryPaperMapper;
import java.util.List;
import com.quotawish.leaveword.model.entity.ExamCategoryPaper;
import com.quotawish.leaveword.service.ExamCategoryPaperService;
@Service
public class ExamCategoryPaperImpl extends ServiceImpl<ExamCategoryPaperMapper, ExamCategoryPaper> implements ExamCategoryPaperService{

    @Override
    public int updateBatch(List<ExamCategoryPaper> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamCategoryPaper> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamCategoryPaper> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamCategoryPaper record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamCategoryPaper record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
