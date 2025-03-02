package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 试卷版本记录表
 */
@Data
@TableName(value = "exam_paper_version")
public class ExamPaperVersion {
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
     * 版本号
     */
    @TableField(value = "version_number")
    @NotNull(message = "版本号不能为null")
    private Integer versionNumber;

    /**
     * 试卷版本内容，包含试题、答案、解析等信息
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建者ID
     */
    @TableField(value = "created_by")
    private Integer createdBy;

    /**
     * 版本创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;
}