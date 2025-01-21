package com.quotawish.leaveword.model.dto.audio;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 编辑音频文件表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class AudioFileEditRequest implements Serializable {

    /**
     * 音频文件的唯一标识符
     */
    private Long id;

    /**
     * 音频文件的路径。
     */
    @NotNull(message = "音频文件的路径不能为空")
    private String path;

    /**
     * 音频文件的内容。
     */
    private String content;
}