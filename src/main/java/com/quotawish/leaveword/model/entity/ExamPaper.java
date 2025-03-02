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
 * 试卷表
 */
@Data
@TableName(value = "exam_paper")
public class ExamPaper {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 试卷名称
     */
    @TableField(value = "`name`")
    @Size(max = 255,message = "试卷名称最大长度要小于 255")
    @NotBlank(message = "试卷名称不能为空")
    private String name;

    /**
     * 缩略图 URL
     */
    @TableField(value = "thumbnail_url")
    @Size(max = 255,message = "缩略图 URL最大长度要小于 255")
    private String thumbnailUrl;

    /**
     * 价格 (单位: 分)
     */
    @TableField(value = "price")
    @NotNull(message = "价格 (单位: 分)不能为null")
    private Integer price;

    /**
     * 试卷描述 (限制 255 字符)
     */
    @TableField(value = "description")
    @Size(max = 255,message = "试卷描述 (限制 255 字符)最大长度要小于 255")
    private String description;

    /**
     * 总题数
     */
    @TableField(value = "total_questions")
    private Integer totalQuestions;

    /**
     * 试卷状态
     */
    @TableField(value = "`status`")
    private Object status;

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