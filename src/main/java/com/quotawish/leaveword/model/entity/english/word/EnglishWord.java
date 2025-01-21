package com.quotawish.leaveword.model.entity.english.word;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 单词表
 * @TableName english_word
 */
@TableName(value ="english_word")
@Data
public class EnglishWord implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 单词头部
     */
    private String word_head;

    /**
     * 单词内容
     */
    private String info;

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

    public WordContent transformInfo() {
        return JSONUtil.toBean(this.info, WordContent.class);
    }

}