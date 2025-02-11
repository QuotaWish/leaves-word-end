package com.quotawish.leaveword.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.model.dto.english.status_change.EnglishWordStatusChangeQueryRequest;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.model.vo.english.WordStatusChangeVO;
import com.quotawish.leaveword.service.WordStatusChangeService;
import com.quotawish.leaveword.mapper.WordStatusChangeMapper;
import com.quotawish.leaveword.utils.SqlUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author TalexDS
* @description 针对表【word_status_change(单词状态变更记录表)】的数据库操作Service实现
* @createDate 2025-01-24 12:47:41
*/
@Service
public class WordStatusChangeServiceImpl extends ServiceImpl<WordStatusChangeMapper, WordStatusChange>
    implements WordStatusChangeService{
    @Override
    public BaseResponse<List<WordStatusChange>> selectListByWordId(Long wordId, Long lastId) {
        // 如果lastId是-1 那么就获取最新的10条 否则就从lastId往后获取
        QueryWrapper<WordStatusChange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("word_id", wordId);
        if (lastId != -1) {
            queryWrapper.gt("id", lastId);
        }
        // 优先按照update_time排序 然后是create_time
        queryWrapper.orderByDesc("update_time").orderByDesc("create_time").last("LIMIT 10");

        return ResultUtils.success(list(queryWrapper));
    }

    /**
     * 获取查询条件
     *
     * @param englishWordStatusChangeQueryRequest 查询请求参数
     * @return QueryWrapper
     */
    @Override
    public Page<WordStatusChangeVO> getQueryWrapper(EnglishWordStatusChangeQueryRequest englishWordStatusChangeQueryRequest) {
        int current = englishWordStatusChangeQueryRequest.getCurrent();
        int size = englishWordStatusChangeQueryRequest.getPageSize();

        MPJLambdaWrapper<WordStatusChange> mpjLambdaWrapper = new MPJLambdaWrapper<WordStatusChange>()
                        .selectAll(WordStatusChange.class)
                        .select(EnglishWord::getWord_head, EnglishWord::getStatus)
                                .selectAs(EnglishWord::getWord_head, WordStatusChangeVO::getWord_head)
                                .selectAs(EnglishWord::getStatus, WordStatusChangeVO::getStatus)
                                        .leftJoin(EnglishWord.class, EnglishWord::getId, WordStatusChange::getWordId);

        // 模糊查询条件
        if (StringUtils.isNotBlank(englishWordStatusChangeQueryRequest.getComment())) {
            mpjLambdaWrapper.like(WordStatusChange::getComment, englishWordStatusChangeQueryRequest.getComment().trim());
        }

        if (Objects.equals(englishWordStatusChangeQueryRequest.getSortOrder(), CommonConstant.SORT_ORDER_ASC)) {
            mpjLambdaWrapper.orderByAsc(WordStatusChange::getCreateTime, WordStatusChange::getUpdateTime);
        } else {
            mpjLambdaWrapper.orderByDesc(WordStatusChange::getCreateTime, WordStatusChange::getUpdateTime);
        }

        Page<WordStatusChangeVO> wordStatusChangeVOPage = new Page<>(current, size);

        return baseMapper.selectJoinPage(wordStatusChangeVOPage, WordStatusChangeVO.class, mpjLambdaWrapper);
    }

}
