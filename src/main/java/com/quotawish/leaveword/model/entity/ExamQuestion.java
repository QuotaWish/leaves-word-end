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
 * 试题表
 */
@Data
@TableName(value = "exam_question")
public class ExamQuestion {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 题目内容
     */
    @TableField(value = "question_text")
    @NotBlank(message = "题目内容不能为空")
    private String questionText;

    /**
     * 题目类型
     */
    @TableField(value = "question_type")
    private Object questionType;

    /**
     * 题目选项，建议以 JSON 格式存储
     */
    @TableField(value = "`options`")
    private String options;

    /**
     * 答案
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 答案解析
     */
    @TableField(value = "explanation")
    private String explanation;

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
}