package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户配置表
 */
@Data
@TableName(value = "user_config")
public class UserConfig {
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    @NotNull(message = "用户ID不能为null")
    private Long userId;

    /**
     * 用户配置JSON数据
     */
    @TableField(value = "config_json")
    @NotBlank(message = "用户配置JSON数据不能为空")
    private String configJson;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
} 