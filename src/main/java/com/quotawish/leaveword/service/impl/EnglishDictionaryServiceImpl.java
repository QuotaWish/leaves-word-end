package com.quotawish.leaveword.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.mapper.EnglishDictionaryMapper;
import com.quotawish.leaveword.model.dto.english.english_dictionary.EnglishDictionaryQueryRequest;
import com.quotawish.leaveword.model.entity.english.EnglishDictionary;
import com.quotawish.leaveword.model.vo.english.EnglishDictionaryVO;
import com.quotawish.leaveword.service.EnglishDictionaryService;
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
 * 英语词典服务实现
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Service
@Slf4j
public class EnglishDictionaryServiceImpl extends ServiceImpl<EnglishDictionaryMapper, EnglishDictionary> implements EnglishDictionaryService {

    @Resource
    private UserService userService;

    /**
     * 获取查询条件
     *
     * @param english_dictionaryQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<EnglishDictionary> getQueryWrapper(EnglishDictionaryQueryRequest english_dictionaryQueryRequest) {
        QueryWrapper<EnglishDictionary> queryWrapper = new QueryWrapper<>();
        if (english_dictionaryQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = english_dictionaryQueryRequest.getId();
        Long notId = english_dictionaryQueryRequest.getNotId();
        String title = english_dictionaryQueryRequest.getTitle();
        String content = english_dictionaryQueryRequest.getContent();
        String searchText = english_dictionaryQueryRequest.getSearchText();
        String sortField = english_dictionaryQueryRequest.getSortField();
        String sortOrder = english_dictionaryQueryRequest.getSortOrder();
        List<String> tagList = english_dictionaryQueryRequest.getTags();
        Long userId = english_dictionaryQueryRequest.getUserId();
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
     * 获取英语词典封装
     *
     * @param english_dictionary
     * @param request
     * @return
     */
    @Override
    public EnglishDictionaryVO getEnglishDictionaryVO(EnglishDictionary english_dictionary, HttpServletRequest request) {

        return EnglishDictionaryVO.objToVo(english_dictionary);
    }

    /**
     * 分页获取英语词典封装
     *
     * @param english_dictionaryPage
     * @param request
     * @return
     */
    @Override
    public Page<EnglishDictionaryVO> getEnglishDictionaryVOPage(Page<EnglishDictionary> english_dictionaryPage, HttpServletRequest request) {
        List<EnglishDictionary> english_dictionaryList = english_dictionaryPage.getRecords();
        Page<EnglishDictionaryVO> english_dictionaryVOPage = new Page<>(english_dictionaryPage.getCurrent(), english_dictionaryPage.getSize(), english_dictionaryPage.getTotal());
        if (CollUtil.isEmpty(english_dictionaryList)) {
            return english_dictionaryVOPage;
        }
        // 对象列表 => 封装对象列表
        List<EnglishDictionaryVO> english_dictionaryVOList = english_dictionaryList.stream().map(english_dictionary -> {
            return EnglishDictionaryVO.objToVo(english_dictionary);
        }).collect(Collectors.toList());

        english_dictionaryVOPage.setRecords(english_dictionaryVOList);
        return english_dictionaryVOPage;
    }

}
