package com.quotawish.leaveword.model.entity.audio;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName media_creator
 */
@TableName(value ="media_creator")
@Data
public class MediaCreator {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long word_id;

    /**
     * 
     */
    private String media_type;

    /**
     * 
     */
    private String media_url;

    /**
     * 
     */
    private Long creator_id;

    /**
     * 
     */
    private String info;

    /**
     * 
     */
    private Date created_at;
}