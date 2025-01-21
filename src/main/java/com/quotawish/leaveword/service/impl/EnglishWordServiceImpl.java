package com.quotawish.leaveword.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.mapper.EnglishWordMapper;
import com.quotawish.leaveword.model.dto.english.english_word.EnglishWordQueryRequest;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.vo.english.EnglishWordVO;
import com.quotawish.leaveword.service.EnglishWordService;
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
 * 英语单词服务实现
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Service
@Slf4j
public class EnglishWordServiceImpl extends ServiceImpl<EnglishWordMapper, EnglishWord> implements EnglishWordService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     *
     * @param english_word
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validEnglishWord(EnglishWord english_word, boolean add) {
        ThrowUtils.throwIf(english_word == null, ErrorCode.PARAMS_ERROR);

    }

    /**
     * 获取查询条件
     *
     * @param english_wordQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<EnglishWord> getQueryWrapper(EnglishWordQueryRequest english_wordQueryRequest) {
        QueryWrapper<EnglishWord> queryWrapper = new QueryWrapper<>();
        if (english_wordQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = english_wordQueryRequest.getId();
        Long notId = english_wordQueryRequest.getNotId();
        String title = english_wordQueryRequest.getTitle();
        String content = english_wordQueryRequest.getContent();
        String searchText = english_wordQueryRequest.getSearchText();
        String sortField = english_wordQueryRequest.getSortField();
        String sortOrder = english_wordQueryRequest.getSortOrder();
        List<String> tagList = english_wordQueryRequest.getTags();
        Long userId = english_wordQueryRequest.getUserId();
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
     * 获取英语单词封装
     *
     * @param english_word
     * @param request
     * @return
     */
    @Override
    public EnglishWordVO getEnglishWordVO(EnglishWord english_word, HttpServletRequest request) {
        // 对象转封装类
        EnglishWordVO english_wordVO = EnglishWordVO.objToVo(english_word);

        return english_wordVO;
    }

    /**
     * 分页获取英语单词封装
     *
     * @param english_wordPage
     * @param request
     * @return
     */
    @Override
    public Page<EnglishWordVO> getEnglishWordVOPage(Page<EnglishWord> english_wordPage, HttpServletRequest request) {
        List<EnglishWord> english_wordList = english_wordPage.getRecords();
        Page<EnglishWordVO> english_wordVOPage = new Page<>(english_wordPage.getCurrent(), english_wordPage.getSize(), english_wordPage.getTotal());
        if (CollUtil.isEmpty(english_wordList)) {
            return english_wordVOPage;
        }
        // 对象列表 => 封装对象列表
        List<EnglishWordVO> english_wordVOList = english_wordList.stream().map(english_word -> {
            return EnglishWordVO.objToVo(english_word);
        }).collect(Collectors.toList());

        english_wordVOPage.setRecords(english_wordVOList);
        return english_wordVOPage;
    }

}
