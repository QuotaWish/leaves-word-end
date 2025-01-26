package com.quotawish.leaveword.model.dto.audio;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 创建音频文件表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class AudioFileAddRequest implements Serializable {

    /**
     * 音频文件的内容。
     */
    @NotNull(message = "音频文件的内容不能为空")
    @Length(max = 255, message = "音频内容长度不能超过255个字符")
    private String content;

    /**
     * 音频文件的名称。
     */
    @NotNull(message = "音频文件的名称不能为空")
    private String name;

    /**
     * 音频文件的音色。
     */
    @NotBlank(message = "音频音色不能为空")
    @Length(max = 255, message = "音频音色长度不能超过255个字符")
    private String voice;
}