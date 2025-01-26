package com.quotawish.leaveword.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coze.openapi.client.workflows.run.ResumeRunReq;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.client.workflows.run.model.WorkflowEventError;
import com.coze.openapi.client.workflows.run.model.WorkflowEventType;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.manager.CozeManager;
import com.quotawish.leaveword.mapper.MediaCreatorMapper;
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorQueryRequest;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.audio.MediaCreator;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.enums.MediaType;
import com.quotawish.leaveword.model.vo.MediaCreatorVO;
import com.quotawish.leaveword.model.vo.UserVO;
import com.quotawish.leaveword.service.EnglishWordService;
import com.quotawish.leaveword.service.MediaCreatorService;
import com.quotawish.leaveword.service.UserService;
import com.quotawish.leaveword.utils.SqlUtils;
import io.reactivex.Flowable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 媒体创建表服务实现
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Service
@Slf4j
public class MediaCreatorServiceImpl extends ServiceImpl<MediaCreatorMapper, MediaCreator> implements MediaCreatorService {

    @Resource
    private UserService userService;

    @Resource
    private EnglishWordService englishWordService;

    @Resource
    private CozeManager cozeManager;

    /**
     * 获取查询条件
     *
     * @param media_creatorQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<MediaCreator> getQueryWrapper(MediaCreatorQueryRequest media_creatorQueryRequest) {
        QueryWrapper<MediaCreator> queryWrapper = new QueryWrapper<>();
        if (media_creatorQueryRequest == null) {
            return queryWrapper;
        }

        Long id = media_creatorQueryRequest.getId();
        Long notId = media_creatorQueryRequest.getNotId();
        String mediaType = media_creatorQueryRequest.getMediaType();
        String sortField = media_creatorQueryRequest.getSortField();
        String sortOrder = media_creatorQueryRequest.getSortOrder();
        Long userId = media_creatorQueryRequest.getUserId();

        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(mediaType), "mediaType", mediaType);

        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        return queryWrapper;
    }

    /**
     * 获取媒体创建表封装
     *
     * @param media_creator
     * @param request
     * @return
     */
    @Override
    public MediaCreatorVO getMediaCreatorVO(MediaCreator media_creator, HttpServletRequest request) {
        // 对象转封装类
        MediaCreatorVO media_creatorVO = MediaCreatorVO.objToVo(media_creator);

        return media_creatorVO;
    }

    /**
     * 分页获取媒体创建表封装
     *
     * @param media_creatorPage
     * @param request
     * @return
     */
    @Override
    public Page<MediaCreatorVO> getMediaCreatorVOPage(Page<MediaCreator> media_creatorPage, HttpServletRequest request) {
        List<MediaCreator> media_creatorList = media_creatorPage.getRecords();
        Page<MediaCreatorVO> media_creatorVOPage = new Page<>(media_creatorPage.getCurrent(), media_creatorPage.getSize(), media_creatorPage.getTotal());
        if (CollUtil.isEmpty(media_creatorList)) {
            return media_creatorVOPage;
        }
        // 对象列表 => 封装对象列表
        List<MediaCreatorVO> media_creatorVOList = media_creatorList.stream().map(media_creator -> {
            return MediaCreatorVO.objToVo(media_creator);
        }).collect(Collectors.toList());

        media_creatorVOPage.setRecords(media_creatorVOList);
        return media_creatorVOPage;
    }

    @SneakyThrows
    @Override
    public SseEmitter startWorkFlow(@NotNull MediaType media_type, Long word_id, Long creator) {
        Long startTime = System.currentTimeMillis();
        EnglishWord word = englishWordService.getById(word_id);
        ThrowUtils.throwIf(word == null || word.getWord_head() == null, ErrorCode.NOT_FOUND_ERROR);

        MediaCreator mediaCreator = new MediaCreator();
        mediaCreator.setCreator_id(creator);
        mediaCreator.setMedia_type(media_type.name());
        mediaCreator.setWord_id(word_id);

        boolean save = save(mediaCreator);
        ThrowUtils.throwIf(!save, ErrorCode.OPERATION_ERROR);

        Map<String, Object> data = new HashMap<>();
        data.put("input", word.getWord_head());

        log.info("开始启动工作流 对应单词：{} | {}", word.getWord_head(), media_type);

        SseEmitter emitter = new SseEmitter();

        emitter.send(SseEmitter.event().data("waiting").build());

        JSONObject jsonObj = JSONUtil.createObj();
        JSONArray jsonArray = new JSONArray();

        jsonObj.putOnce("input", word.getWord_head());

        // 开始启动工作流
        Flowable<WorkflowEvent> events = cozeManager.startWorkflow(cozeManager.getConfig().getWorkflowId(), data);

        final Long[] lastNodeTs = {startTime};

        events.subscribe(
                event -> {
                    emitter.send(SseEmitter.event().id(event.getLogID()).name(event.getEvent().getValue()).data(event.getMessage()).build());

                    if (event.getEvent().equals(WorkflowEventType.MESSAGE)) {
                        Long time = System.currentTimeMillis() - startTime;

                        // 计算每个节点的耗时
                        Long nodeTime = time - lastNodeTs[0];
                        lastNodeTs[0] = time;

                        JSONObject node = JSONUtil.createObj();

                        node.putOnce("event", event.getEvent().getValue());
                        node.putOnce("message", event.getMessage());
                        node.putOnce("costs", nodeTime);

                        if (Objects.equals(event.getMessage().getNodeTitle(), "End")) {
                            JSONObject entries = JSONUtil.parseObj(event.getMessage().getContent());
                            String output = entries.getStr("output");

                            if (StringUtils.isNotBlank(output)) {
                                node.putOnce("output", output);
                                jsonArray.add(node);

                                mediaCreator.setMedia_url(output);
                            }
                        }

                        jsonArray.add(node);
                    } else {
                        if (event.getEvent().equals(WorkflowEventType.DONE)) {
                            jsonObj.putOnce("workflow", jsonArray);
                            jsonObj.putOnce("status", "DONE");

                            emitter.complete();

                        } else if (event.getEvent().equals(WorkflowEventType.ERROR)) {
                            jsonObj.putOnce("status", "ERROR");
                            jsonObj.putOnce("error", event.getError());

                            WorkflowEventError error = event.getError();

                            int code = error.getErrorCode();
                            String msg = error.getErrorMessage();

                            emitter.completeWithError(new RuntimeException(code + " | " + msg));
                        }  else if (event.getEvent().equals(WorkflowEventType.INTERRUPT)) {
                            jsonObj.putOnce("status", "INTERRUPT");
                            emitter.completeWithError(new RuntimeException("INTERRUPT"));
                        }

                        Long endTime = System.currentTimeMillis();

                        jsonObj.putOnce("log_id", event.getLogID());
                        jsonObj.putOnce("costs", endTime - startTime);

                        mediaCreator.setInfo(jsonObj.toString());

                        updateById(mediaCreator);
                    }
                },
                Throwable::printStackTrace);

        return emitter;
    }

}
