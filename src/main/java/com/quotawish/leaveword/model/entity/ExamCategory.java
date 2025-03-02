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
 * 试卷分类表
 */
@Data
@TableName(value = "exam_category")
public class ExamCategory {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 分类名称
     */
    @TableField(value = "`name`")
    @Size(max = 255,message = "分类名称最大长度要小于 255")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /**
     * 父级分类ID
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 排序值，控制前端显示顺序
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 逻辑删除标志 (0: 正常, 1: 删除)
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    /**
     * 创建者ID
     */
    @TableField(value = "created_by")
    private Integer createdBy;

    /**
     * 更新者ID
     */
    @TableField(value = "updated_by")
    private Integer updatedBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private Date updatedAt;

    /**
     * 删除时间
     */
    @TableField(value = "deleted_at")
    private Date deletedAt;
}