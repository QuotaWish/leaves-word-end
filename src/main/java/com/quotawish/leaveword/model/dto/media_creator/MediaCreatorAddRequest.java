package com.quotawish.leaveword.model.dto.media_creator;

import com.quotawish.leaveword.model.enums.MediaType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建媒体创建表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class MediaCreatorAddRequest implements Serializable {


    /**
     * 关联的单词ID
     */
    @NotNull(message = "关联的单词ID不能为空")
    private Long wordId;

    /**
     * 媒体类型
     */
    @NotNull(message = "媒体类型不能为空")
    private MediaType mediaType;

    private static final long serialVersionUID = 1L;
}