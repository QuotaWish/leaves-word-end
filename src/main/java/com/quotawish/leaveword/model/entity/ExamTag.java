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
 * 试卷标签表
 */
@Data
@TableName(value = "exam_tag")
public class ExamTag {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 标签名称
     */
    @TableField(value = "`name`")
    @Size(max = 50,message = "标签名称最大长度要小于 50")
    @NotBlank(message = "标签名称不能为空")
    private String name;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;
}