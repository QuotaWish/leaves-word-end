package com.quotawish.leaveword.model.dto.audio;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 创建音频文件表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class AudioFileSynthesizeRequest implements Serializable {

    /**
     * 音频文件的唯一标识符
     */
    @NotBlank(message = "音频ID不能为空")
    private Long id;
}