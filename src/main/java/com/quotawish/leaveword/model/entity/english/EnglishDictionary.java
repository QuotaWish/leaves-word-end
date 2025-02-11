package com.quotawish.leaveword.model.entity.english;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 英语词书
 * @TableName english_dictionary
 */
@TableName(value = "english_dictionary")
@Data
public class EnglishDictionary implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 书籍名称
     */
    private String name;

    /**
     * 书籍描述
     */
    private String description;

    /**
     * 书籍图片URL
     */
    private String image_url;

    /**
     * 作者
     */
    private String author;

    /**
     * ISBN编号
     */
    private String isbn;

    /**
     * 出版日期
     */
    private String publication_date;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新时间
     */
    private Date update_time;

    private int total_words, published_words, approved_words;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer is_delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}