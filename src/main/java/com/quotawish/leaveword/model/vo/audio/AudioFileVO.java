package com.quotawish.leaveword.model.vo.audio;

import cn.hutool.json.JSONUtil;
import com.quotawish.leaveword.model.entity.audio.AudioFile;
import com.quotawish.leaveword.model.enums.AudioFileStatus;
import com.quotawish.leaveword.model.vo.UserVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 音频文件表视图
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class AudioFileVO implements Serializable {

    /**
     * id
     */
    private Long id;


    /**
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建用户 id
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 音频文件的状态。
     */
    private AudioFileStatus status;

    /**
     * 封装类转对象
     *
     * @param audio_fileVO
     * @return
     */
    public static AudioFile voToObj(AudioFileVO audio_fileVO) {
        if (audio_fileVO == null) {
            return null;
        }
        AudioFile audio_file = new AudioFile();
        BeanUtils.copyProperties(audio_fileVO, audio_file);
        audio_file.setStatus(audio_fileVO.getStatus());
        return audio_file;
    }

    /**
     * 对象转封装类
     *
     * @param audio_file
     * @return
     */
    public static AudioFileVO objToVo(AudioFile audio_file) {
        if (audio_file == null) {
            return null;
        }
        AudioFileVO audio_fileVO = new AudioFileVO();
        BeanUtils.copyProperties(audio_file, audio_fileVO);
        audio_fileVO.setStatus(audio_file.getStatus());
        return audio_fileVO;
    }
}
