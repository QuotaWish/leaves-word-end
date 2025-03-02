package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 试卷与分类关联表
 */
@Data
@TableName(value = "exam_category_paper")
public class ExamCategoryPaper {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 试卷分类ID
     */
    @TableField(value = "category_id")
    @NotNull(message = "试卷分类ID不能为null")
    private Integer categoryId;

    /**
     * 试卷ID
     */
    @TableField(value = "paper_id")
    @NotNull(message = "试卷ID不能为null")
    private Integer paperId;

    /**
     * 分类内排序
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 关联创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private Date updatedAt;
}