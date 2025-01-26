package com.quotawish.leaveword.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotawish.leaveword.annotation.AuthCheck;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.DeleteRequest;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.UserConstant;
import com.quotawish.leaveword.exception.BusinessException;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.model.dto.audio.*;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.audio.AudioFile;
import com.quotawish.leaveword.model.enums.AudioFileStatus;
import com.quotawish.leaveword.model.vo.audio.AudioFileVO;
import com.quotawish.leaveword.service.AudioFileService;
import com.quotawish.leaveword.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 音频文件表接口
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@RestController
@RequestMapping("/audio_file")
@Slf4j
public class AudioFileController {

    @Resource
    private AudioFileService audio_fileService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建音频文件表
     *
     * @param audio_fileAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addAudioFile(@RequestBody AudioFileAddRequest audio_fileAddRequest, HttpServletRequest request) {
        // 申请合成 然后就创建一条数据库记录 等待后续合成
        AudioFile audio_file = new AudioFile();
        BeanUtils.copyProperties(audio_fileAddRequest, audio_file);

        User loginUser = userService.getLoginUser(request);
        audio_file.setStatus(AudioFileStatus.IN_QUEUE);
        audio_file.setCreator_id(loginUser.getId());

        JSONObject jsonObject = JSONUtil.createObj()
                .putOnce("value", audio_file.getContent())
                .putOnce("voice", audio_fileAddRequest.getVoice())
                .putOnce("err", "");

        audio_file.setContent(Base64.encode(jsonObject.toString()));

        boolean result = audio_fileService.save(audio_file);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        long newAudioFileId = audio_file.getId();

        return ResultUtils.success(newAudioFileId);
    }

    /**
     * 针对某个File进行合成操作
     */
    @PostMapping("/synthesize")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> synthesize(@RequestBody AudioFileSynthesizeRequest audioFileSynthesizeRequest, HttpServletRequest request) {

        audio_fileService.synthesizeAudioFile(audioFileSynthesizeRequest.getId());

        return ResultUtils.success(true);
    }

    /**
     * 搜索音频文件
     */
    @PostMapping("/search")
    public BaseResponse<Page<AudioFileVO>> searchAudioFile(@RequestBody AudioFileQueryRequest audio_fileQueryRequest, HttpServletRequest request) {

        QueryWrapper<AudioFile> audioFilePage = audio_fileService.getQueryWrapper(audio_fileQueryRequest);
        Page<AudioFile> page = audio_fileService.page(new Page<>(audio_fileQueryRequest.getCurrent(), audio_fileQueryRequest.getPageSize()), audioFilePage);

        return ResultUtils.success(audio_fileService.getAudioFileVOPage(page, request));
    }

    /**
     * 上传某个音频
     */
    @PostMapping("/upload")
    public BaseResponse<Boolean> uploadAudioFile(@RequestBody AudioFileQueryRequest audio_fileQueryRequest) {

        audio_fileService.uploadAudioFile(audio_fileQueryRequest.getId());

        return ResultUtils.success(true);
    }

    /**
     * 删除音频文件表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteAudioFile(@RequestBody @Validated DeleteRequest deleteRequest, HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        AudioFile oldAudioFile = audio_fileService.getById(id);
        ThrowUtils.throwIf(oldAudioFile == null, ErrorCode.NOT_FOUND_ERROR);

        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = audio_fileService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(true);
    }



    /**
     * 根据 id 获取音频文件表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<AudioFileVO> getAudioFileVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        AudioFile audio_file = audio_fileService.getById(id);
        ThrowUtils.throwIf(audio_file == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(audio_fileService.getAudioFileVO(audio_file, request));
    }

    /**
     * 分页获取音频文件表列表（仅管理员可用）
     *
     * @param audio_fileQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<AudioFile>> listAudioFileByPage(@RequestBody AudioFileQueryRequest audio_fileQueryRequest) {
        long current = audio_fileQueryRequest.getCurrent();
        long size = audio_fileQueryRequest.getPageSize();
        // 查询数据库
        Page<AudioFile> audio_filePage = audio_fileService.page(new Page<>(current, size),
                audio_fileService.getQueryWrapper(audio_fileQueryRequest));
        return ResultUtils.success(audio_filePage);
    }

    /**
     * 分页获取音频文件表列表（封装类）
     *
     * @param audio_fileQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<AudioFileVO>> listAudioFileVOByPage(@RequestBody AudioFileQueryRequest audio_fileQueryRequest,
                                                               HttpServletRequest request) {
        long current = audio_fileQueryRequest.getCurrent();
        long size = audio_fileQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<AudioFile> audio_filePage = audio_fileService.page(new Page<>(current, size),
                audio_fileService.getQueryWrapper(audio_fileQueryRequest));
        // 获取封装类
        return ResultUtils.success(audio_fileService.getAudioFileVOPage(audio_filePage, request));
    }
//
//    /**
//     * 分页获取当前登录用户创建的音频文件表列表
//     *
//     * @param audio_fileQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/my/list/page/vo")
//    public BaseResponse<Page<AudioFileVO>> listMyAudioFileVOByPage(@RequestBody AudioFileQueryRequest audio_fileQueryRequest,
//                                                                 HttpServletRequest request) {
//        ThrowUtils.throwIf(audio_fileQueryRequest == null, ErrorCode.PARAMS_ERROR);
//        // 补充查询条件，只查询当前登录用户的数据
//        User loginUser = userService.getLoginUser(request);
//        audio_fileQueryRequest.setUserId(loginUser.getId());
//        long current = audio_fileQueryRequest.getCurrent();
//        long size = audio_fileQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        // 查询数据库
//        Page<AudioFile> audio_filePage = audio_fileService.page(new Page<>(current, size),
//                audio_fileService.getQueryWrapper(audio_fileQueryRequest));
//        // 获取封装类
//        return ResultUtils.success(audio_fileService.getAudioFileVOPage(audio_filePage, request));
//    }
//
//    /**
//     * 编辑音频文件表（给用户使用）
//     *
//     * @param audio_fileEditRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/edit")
//    public BaseResponse<Boolean> editAudioFile(@RequestBody AudioFileEditRequest audio_fileEditRequest, HttpServletRequest request) {
//        if (audio_fileEditRequest == null || audio_fileEditRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // todo 在此处将实体类和 DTO 进行转换
//        AudioFile audio_file = new AudioFile();
//        BeanUtils.copyProperties(audio_fileEditRequest, audio_file);
//        // 数据校验
//        audio_fileService.validAudioFile(audio_file, false);
//        User loginUser = userService.getLoginUser(request);
//        // 判断是否存在
//        long id = audio_fileEditRequest.getId();
//        AudioFile oldAudioFile = audio_fileService.getById(id);
//        ThrowUtils.throwIf(oldAudioFile == null, ErrorCode.NOT_FOUND_ERROR);
//        // 仅本人或管理员可编辑
//        if (!oldAudioFile.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
//        // 操作数据库
//        boolean result = audio_fileService.updateById(audio_file);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(true);
//    }
//
//    // endregion
}
