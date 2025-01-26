package com.quotawish.leaveword.model.entity.english.word;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 单词变更记录表
 * 只有在发布之后才会存储 方便回滚
 * @TableName english_word_change_log
 */
@TableName(value ="english_word_change_log")
@Data
public class EnglishWordChangeLog {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 单词ID
     */
    private Long english_word_id;

    /**
     * 变更字段名
     */
    private String field_name;

    /**
     * 变更前的值
     */
    private String old_value;

    /**
     * 变更后的值
     */
    private String new_value;

    /**
     * 变更时间
     */
    @TableLogic
    private Date change_time;

    /**
     * 变更操作人ID
     */
    private Long changed_by;
}