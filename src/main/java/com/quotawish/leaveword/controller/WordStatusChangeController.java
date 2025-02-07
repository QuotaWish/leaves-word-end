package com.quotawish.leaveword.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotawish.leaveword.annotation.AuthCheck;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.UserConstant;
import com.quotawish.leaveword.model.dto.audio.AudioFileQueryRequest;
import com.quotawish.leaveword.model.dto.english.status_change.EnglishWordStatusChangeQueryRequest;
import com.quotawish.leaveword.model.entity.audio.AudioFile;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.service.impl.WordStatusChangeServiceImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 单词状态变更记录表(word_status_change)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/word_status_change")
public class WordStatusChangeController {
/**
* 服务对象
*/
    @Autowired
    private WordStatusChangeServiceImpl wordStatusChangeServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public WordStatusChange selectOne(Integer id) {
    return wordStatusChangeServiceImpl.getOne(wordStatusChangeServiceImpl.query().eq("id", id));
    }

    /**
     * 根据某个单词获取单词状态变更记录（分页） 默认返回前10条记录 传入记录id获取更早的记录
     */
    @GetMapping("/list/record")
    public Object listRecord(@RequestParam() Long wordId,
                             @RequestParam(required = false, defaultValue = "-1") Long lastId) {
        return wordStatusChangeServiceImpl.selectListByWordId(wordId, lastId);
    }

    /**
     * 分页获取音频文件表列表（仅管理员可用）
     *
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<WordStatusChange>> listStatusChangeByPage(@RequestBody EnglishWordStatusChangeQueryRequest request) {
        long current = request.getCurrent();
        long size = request.getPageSize();
        // 查询数据库
        Page<WordStatusChange> audio_filePage = wordStatusChangeServiceImpl.page(new Page<>(current, size),
                wordStatusChangeServiceImpl.getQueryWrapper(request));

        return ResultUtils.success(audio_filePage);
    }

}
