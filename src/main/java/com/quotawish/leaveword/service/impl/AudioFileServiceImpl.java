package com.quotawish.leaveword.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.audio.AudioFileQueryRequest;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.audio.AudioFile;
import com.quotawish.leaveword.model.enums.AudioFileStatus;
import com.quotawish.leaveword.model.vo.UserVO;
import com.quotawish.leaveword.model.vo.audio.AudioFileVO;
import com.quotawish.leaveword.service.AudioFileService;
import com.quotawish.leaveword.mapper.AudioFileMapper;
import com.quotawish.leaveword.service.UserService;
import com.quotawish.leaveword.utils.SqlUtils;
import com.quotawish.leaveword.utils.SynthesizeUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author TalexDS
* @description 针对表【audio_file(音频文件表)】的数据库操作Service实现
* @createDate 2025-01-20 22:41:12
*/
@Service
public class AudioFileServiceImpl extends ServiceImpl<AudioFileMapper, AudioFile>
    implements AudioFileService{

    @Resource
    private UserService userService;

    /**
     * 获取查询条件
     *
     * @param audio_fileQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<AudioFile> getQueryWrapper(AudioFileQueryRequest audio_fileQueryRequest) {
        QueryWrapper<AudioFile> queryWrapper = new QueryWrapper<>();
        if (audio_fileQueryRequest == null) {
            return queryWrapper;
        }

        Long id = audio_fileQueryRequest.getId();
        String content = audio_fileQueryRequest.getContent();
        String searchText= audio_fileQueryRequest.getQuery();

        String sortField = audio_fileQueryRequest.getSortField();
        String sortOrder = audio_fileQueryRequest.getSortOrder();

        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("title", searchText).or().like("content", searchText));
        }

        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);

        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        return queryWrapper;
    }

    /**
     * 获取音频文件表封装
     *
     * @param audio_file
     * @param request
     * @return
     */
    @Override
    public AudioFileVO getAudioFileVO(AudioFile audio_file, HttpServletRequest request) {
        // 对象转封装类
        AudioFileVO audio_fileVO = AudioFileVO.objToVo(audio_file);

        return audio_fileVO;
    }

    /**
     * 分页获取音频文件表封装
     *
     * @param audio_filePage
     * @param request
     * @return
     */
    @Override
    public Page<AudioFileVO> getAudioFileVOPage(Page<AudioFile> audio_filePage, HttpServletRequest request) {
        List<AudioFile> audio_fileList = audio_filePage.getRecords();
        Page<AudioFileVO> audio_fileVOPage = new Page<>(audio_filePage.getCurrent(), audio_filePage.getSize(), audio_filePage.getTotal());
        if (CollUtil.isEmpty(audio_fileList)) {
            return audio_fileVOPage;
        }
        // 对象列表 => 封装对象列表
        List<AudioFileVO> audio_fileVOList = audio_fileList.stream().map(audio_file -> AudioFileVO.objToVo(audio_file)).collect(Collectors.toList());

        return audio_fileVOPage;
    }

    @Async
    @Override
    public void synthesizeAudioFile(long id, String voice) {
        //调用工具类进行合成 并且上传文件流
        // 先获取到对应的AudioFile
        AudioFile audioFile = getById(id);
        if (audioFile == null) {
            throw new RuntimeException("音频文件不存在");
        }

        // 获取 JSON content
        String content = audioFile.getContent();
        if (StringUtils.isBlank(content)) {
            throw new RuntimeException("音频文件内容不能为空");
        }

        JSONObject jsonObject = new JSONObject(content);

        String value = jsonObject.getStr("value");

        if (StringUtils.isBlank(value)) {
            throw new RuntimeException("音频文件内容不能为空");
        }

        audioFile.setStatus(AudioFileStatus.SYNTHESIZING);

        // apply to db
        updateById(audioFile);

        // start to synthesize
        byte[] result = SynthesizeUtil.synthesize(voice, value);
        // 把 result 转为 base64
        String base64 = Base64.encode(result);

        jsonObject.put("audio", base64);

        audioFile.setContent(Base64.encode(jsonObject.toString()));
        audioFile.setStatus(AudioFileStatus.PROCESSED);

        updateById(audioFile);

    }

}


