package com.quotawish.leaveword.service;

import java.util.List;
import com.quotawish.leaveword.model.entity.UserConfig;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserConfigService extends IService<UserConfig>{


    int updateBatch(List<UserConfig> list);

    int updateBatchSelective(List<UserConfig> list);

    int batchInsert(List<UserConfig> list);

    int deleteByPrimaryKeyIn(List<Long> list);

    int insertOnDuplicateUpdate(UserConfig record);

    int insertOnDuplicateUpdateSelective(UserConfig record);

}
