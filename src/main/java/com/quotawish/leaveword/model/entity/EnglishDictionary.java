package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 英语词书
 * @TableName english_dictionary
 */
@TableName(value ="english_dictionary")
@Data
public class EnglishDictionary {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private String image_url;

    /**
     * 
     */
    private String author;

    /**
     * 
     */
    private String isbn;

    /**
     * 
     */
    private Date publication_date;

    /**
     * 
     */
    private String publisher;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private Date update_time;

    /**
     * 
     */
    private Integer is_delete;
}