package com.quotawish.leaveword.model.entity.english.word;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 单词状态变更记录表
 * @TableName word_status_change
 */
@TableName(value ="word_status_change")
@Data
public class WordStatusChange {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 单词ID
     */
    private Long word_id;

    /**
     * 变更状态
     */
    private String status;

    /**
     * 变更信息
     */
    private String info;

    /**
     * 评论
     */
    private String comment;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer is_delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}