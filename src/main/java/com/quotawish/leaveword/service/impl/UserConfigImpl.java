package com.quotawish.leaveword.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.mapper.UserConfigMapper;
import com.quotawish.leaveword.model.entity.UserConfig;
import com.quotawish.leaveword.service.UserConfigService;
@Service
public class UserConfigImpl extends ServiceImpl<UserConfigMapper, UserConfig> implements UserConfigService{

    @Override
    public int updateBatch(List<UserConfig> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<UserConfig> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<UserConfig> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int deleteByPrimaryKeyIn(List<Long> list) {
        return baseMapper.deleteByPrimaryKeyIn(list);
    }
    @Override
    public int insertOnDuplicateUpdate(UserConfig record) {
        return baseMapper.insertOnDuplicateUpdate(record);
    }
    @Override
    public int insertOnDuplicateUpdateSelective(UserConfig record) {
        return baseMapper.insertOnDuplicateUpdateSelective(record);
    }
}
