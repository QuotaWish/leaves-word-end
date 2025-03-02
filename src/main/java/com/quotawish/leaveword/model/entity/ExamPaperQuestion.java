package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 试卷与试题关联表
 */
@Data
@TableName(value = "exam_paper_question")
public class ExamPaperQuestion {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 试卷ID
     */
    @TableField(value = "paper_id")
    @NotNull(message = "试卷ID不能为null")
    private Integer paperId;

    /**
     * 试题ID
     */
    @TableField(value = "question_id")
    @NotNull(message = "试题ID不能为null")
    private Integer questionId;

    /**
     * 试题在试卷中的排序
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