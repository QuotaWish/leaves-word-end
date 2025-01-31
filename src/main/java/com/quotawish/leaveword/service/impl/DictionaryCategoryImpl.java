package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.model.entity.DictionaryCategory;
import java.util.List;
import com.quotawish.leaveword.mapper.DictionaryCategoryMapper;
import com.quotawish.leaveword.service.DictionaryCategoryService;
@Service
public class DictionaryCategoryImpl extends ServiceImpl<DictionaryCategoryMapper, DictionaryCategory> implements DictionaryCategoryService{

    @Override
    public int updateBatch(List<DictionaryCategory> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<DictionaryCategory> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<DictionaryCategory> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Integer> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(DictionaryCategory record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(DictionaryCategory record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
