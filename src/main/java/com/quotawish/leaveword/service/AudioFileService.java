package com.quotawish.leaveword.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotawish.leaveword.model.dto.audio.AudioFileQueryRequest;
import com.quotawish.leaveword.model.entity.audio.AudioFile;
import com.quotawish.leaveword.model.vo.audio.AudioFileVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

/**
* @author TalexDS
* @description 针对表【audio_file(音频文件表)】的数据库操作Service
* @createDate 2025-01-20 22:41:12
*/
public interface AudioFileService extends IService<AudioFile> {

    /**
     * 获取查询条件
     *
     * @param audio_fileQueryRequest
     * @return
     */
    QueryWrapper<AudioFile> getQueryWrapper(AudioFileQueryRequest audio_fileQueryRequest);

    /**
     * 获取音频文件表封装
     *
     * @param audio_file
     * @param request
     * @return
     */
    AudioFileVO getAudioFileVO(AudioFile audio_file, HttpServletRequest request);

    /**
     * 分页获取音频文件表封装
     *
     * @param audio_filePage
     * @param request
     * @return
     */
    Page<AudioFileVO> getAudioFileVOPage(Page<AudioFile> audio_filePage, HttpServletRequest request);

    /**
     * 合成音频文件
     */
    void synthesizeAudioFile(long id);

    /**
     * 上传音频文件
     */
    void uploadAudioFile(long id);
}
