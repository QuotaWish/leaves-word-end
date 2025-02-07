package com.quotawish.leaveword.model.entity.english.word;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 单词状态变更记录表
 */
@Data
@TableName(value = "word_status_change")
public class WordStatusChange {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "id不能为null")
    private Long id;

    /**
     * 单词ID
     */
    @TableField(value = "word_id")
    @NotNull(message = "单词ID不能为null")
    private Long wordId;

    /**
     * 变更状态
     */
    @TableField(value = "`status`")
    @Size(max = 255,message = "变更状态最大长度要小于 255")
    @NotBlank(message = "变更状态不能为空")
    private String status;

    /**
     * 变更信息
     */
    @TableField(value = "info")
    private String info;

    /**
     * 评论
     */
    @TableField(value = "`comment`")
    @Size(max = 255,message = "评论最大长度要小于 255")
    private String comment;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;
}