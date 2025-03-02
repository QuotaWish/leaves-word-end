package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.model.entity.ExamCategory;
import java.util.List;
import com.quotawish.leaveword.mapper.ExamCategoryMapper;
import com.quotawish.leaveword.service.ExamCategoryService;
@Service
public class ExamCategoryImpl extends ServiceImpl<ExamCategoryMapper, ExamCategory> implements ExamCategoryService{

    @Override
    public int updateBatch(List<ExamCategory> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<ExamCategory> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<ExamCategory> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(ExamCategory record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(ExamCategory record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
