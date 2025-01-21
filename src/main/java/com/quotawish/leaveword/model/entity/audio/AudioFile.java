package com.quotawish.leaveword.model.entity.audio;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.enums.AudioFileStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 音频文件实体类，用于存储音频文件的信息。
 */
@TableName(value = "audio_file")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioFile implements Serializable {

    /**
     * 音频文件的唯一标识符，自增ID。
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 音频文件的路径。
     */
    private String path;

    /**
     * 音频文件的名称。
     */
    private String name;

    /**
     * 音频文件的内容。
     */
    private String content;

    /**
     * 创建人的用户ID。
     */
    private Long creator_id;

//    /**
//     * 创建人信息。
//     */
//    @TableField(exist = false)
//    private User creator;

    /**
     * 音频文件的创建时间。
     */
    private Date create_time;

    /**
     * 音频文件的最后更新时间。
     */
    private Date update_time;

    /**
     * 音频文件的状态。
     */
    private AudioFileStatus status;

    /**
     * 逻辑删除标识符，0表示未删除，1表示已删除。
     */
    @TableLogic
    private Integer is_delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}