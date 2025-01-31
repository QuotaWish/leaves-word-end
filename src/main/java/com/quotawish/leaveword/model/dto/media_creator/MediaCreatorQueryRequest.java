package com.quotawish.leaveword.model.dto.media_creator;

import com.quotawish.leaveword.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询媒体创建表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MediaCreatorQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private Long notId;

    private Long userId;

    /**
     * 关联的单词ID
     */
    private Long wordId;

    /**
     * 媒体类型
     */
    private String mediaType;

    /**
     * 媒体地址
     */
    private String mediaUrl;

    private static final long serialVersionUID = 1L;
}