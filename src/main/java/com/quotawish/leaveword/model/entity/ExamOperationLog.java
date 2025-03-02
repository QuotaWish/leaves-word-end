package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 操作日志表
 */
@Data
@TableName(value = "exam_operation_log")
public class ExamOperationLog {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 操作的表名
     */
    @TableField(value = "`table_name`")
    @Size(max = 255,message = "操作的表名最大长度要小于 255")
    @NotBlank(message = "操作的表名不能为空")
    private String tableName;

    /**
     * 记录ID
     */
    @TableField(value = "record_id")
    @NotNull(message = "记录ID不能为null")
    private Integer recordId;

    /**
     * 操作类型
     */
    @TableField(value = "`action`")
    @NotNull(message = "操作类型不能为null")
    private Object action;

    /**
     * 操作员ID
     */
    @TableField(value = "operator_id")
    @NotNull(message = "操作员ID不能为null")
    private Integer operatorId;

    /**
     * 操作时间
     */
    @TableField(value = "operation_time")
    private Date operationTime;

    /**
     * 操作员IP地址
     */
    @TableField(value = "ip_address")
    @Size(max = 45,message = "操作员IP地址最大长度要小于 45")
    private String ipAddress;

    /**
     * 操作说明
     */
    @TableField(value = "description")
    private String description;
}