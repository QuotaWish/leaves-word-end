package com.quotawish.leaveword.model.dto.media_creator;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建媒体创建表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class MediaCreatorAddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}