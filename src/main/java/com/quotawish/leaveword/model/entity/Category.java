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
 * 分类表
 */
@Data
@TableName(value = "category")
public class Category {
    /**
     * 分类ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "分类ID不能为null")
    private Integer id;

    /**
     * 父分类ID
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 分类名称
     */
    @TableField(value = "`name`")
    @Size(max = 100, message = "分类名称最大长度要小于 100")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /**
     * 同级排序顺序
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 分类描述
     */
    @TableField(value = "description")
    @Size(max = 500, message = "分类描述最大长度要小于 500")
    private String description;

    /**
     * 是否为根分类
     */
    @TableField(value = "is_root")
    private Boolean isRoot;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;
}