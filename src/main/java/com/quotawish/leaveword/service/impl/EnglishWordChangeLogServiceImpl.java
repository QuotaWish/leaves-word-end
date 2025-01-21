package com.quotawish.leaveword.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.mapper.EnglishWordChangeLogMapper;
import com.quotawish.leaveword.model.dto.english.english_word.change_log.EnglishWordChangeLogQueryRequest;
import com.quotawish.leaveword.model.entity.english.word.EnglishWordChangeLog;
import com.quotawish.leaveword.model.vo.english.EnglishWordChangeLogVO;
import com.quotawish.leaveword.service.EnglishWordChangeLogService;
import com.quotawish.leaveword.service.UserService;
import com.quotawish.leaveword.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 英语单词变更记录服务实现
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Service
@Slf4j
public class EnglishWordChangeLogServiceImpl extends ServiceImpl<EnglishWordChangeLogMapper, EnglishWordChangeLog> implements EnglishWordChangeLogService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     *
     * @param english_word_change_log
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validEnglishWordChangeLog(EnglishWordChangeLog english_word_change_log, boolean add) {
        ThrowUtils.throwIf(english_word_change_log == null, ErrorCode.PARAMS_ERROR);

    }

    /**
     * 获取查询条件
     *
     * @param english_word_change_logQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<EnglishWordChangeLog> getQueryWrapper(EnglishWordChangeLogQueryRequest english_word_change_logQueryRequest) {
        QueryWrapper<EnglishWordChangeLog> queryWrapper = new QueryWrapper<>();
        if (english_word_change_logQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = english_word_change_logQueryRequest.getId();
        Long notId = english_word_change_logQueryRequest.getNotId();
        String title = english_word_change_logQueryRequest.getTitle();
        String content = english_word_change_logQueryRequest.getContent();
        String searchText = english_word_change_logQueryRequest.getSearchText();
        String sortField = english_word_change_logQueryRequest.getSortField();
        String sortOrder = english_word_change_logQueryRequest.getSortOrder();
        List<String> tagList = english_word_change_logQueryRequest.getTags();
        Long userId = english_word_change_logQueryRequest.getUserId();
        // todo 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("title", searchText).or().like("content", searchText));
        }
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        // JSON 数组查询
        if (CollUtil.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
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
     * 获取英语单词变更记录封装
     *
     * @param english_word_change_log
     * @param request
     * @return
     */
    @Override
    public EnglishWordChangeLogVO getEnglishWordChangeLogVO(EnglishWordChangeLog english_word_change_log, HttpServletRequest request) {
        // 对象转封装类
        EnglishWordChangeLogVO english_word_change_logVO = EnglishWordChangeLogVO.objToVo(english_word_change_log);

        return english_word_change_logVO;
    }

    /**
     * 分页获取英语单词变更记录封装
     *
     * @param english_word_change_logPage
     * @param request
     * @return
     */
    @Override
    public Page<EnglishWordChangeLogVO> getEnglishWordChangeLogVOPage(Page<EnglishWordChangeLog> english_word_change_logPage, HttpServletRequest request) {
        List<EnglishWordChangeLog> english_word_change_logList = english_word_change_logPage.getRecords();
        Page<EnglishWordChangeLogVO> english_word_change_logVOPage = new Page<>(english_word_change_logPage.getCurrent(), english_word_change_logPage.getSize(), english_word_change_logPage.getTotal());
        if (CollUtil.isEmpty(english_word_change_logList)) {
            return english_word_change_logVOPage;
        }
        // 对象列表 => 封装对象列表
        List<EnglishWordChangeLogVO> english_word_change_logVOList = english_word_change_logList.stream().map(english_word_change_log -> {
            return EnglishWordChangeLogVO.objToVo(english_word_change_log);
        }).collect(Collectors.toList());

        english_word_change_logVOPage.setRecords(english_word_change_logVOList);
        return english_word_change_logVOPage;
    }

}
