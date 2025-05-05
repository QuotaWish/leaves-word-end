package com.quotawish.leaveword.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotawish.leaveword.model.entity.UserConfig;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserConfigMapper extends BaseMapper<UserConfig> {
    int updateBatch(@Param("list") List<UserConfig> list);

    int updateBatchSelective(@Param("list") List<UserConfig> list);

    int batchInsert(@Param("list") List<UserConfig> list);

    int deleteByPrimaryKeyIn(List<Long> list);

    int insertOnDuplicateUpdate(UserConfig record);

    int insertOnDuplicateUpdateSelective(UserConfig record);
}