package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 单词和词典关系表
 * @TableName dictionary_word
 */
@TableName(value ="dictionary_word")
@Data
public class DictionaryWord {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long dictionary_id;

    private Long word_id;

    private Date created_at;
}