package com.quotawish.leaveword.model.dto.audio;

import lombok.Data;

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
    private Long id;

    /**
     * 音频文件的音色。
     */
    private String voice;
}