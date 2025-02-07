package com.quotawish.leaveword.model.vo.english;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quotawish.leaveword.model.enums.WordStatus;
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
public class WordStatusChangeVO {

    private Long id;


    private Long wordId;


    private WordStatus status;

    /**
     * 单词头
     */
    private String word_head;

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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}