package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 试卷与标签关联表
 */
@Data
@TableName(value = "exam_paper_tag")
public class ExamPaperTag {
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
     * 标签ID
     */
    @TableField(value = "tag_id")
    @NotNull(message = "标签ID不能为null")
    private Integer tagId;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;
}