package com.quotawish.leaveword.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.constant.CommonConstant;
import com.quotawish.leaveword.exception.ThrowUtils;
import com.quotawish.leaveword.mapper.MediaCreatorMapper;
import com.quotawish.leaveword.model.dto.media_creator.MediaCreatorQueryRequest;
import com.quotawish.leaveword.model.entity.MediaCreator;
import com.quotawish.leaveword.model.entity.MediaCreatorFavour;
import com.quotawish.leaveword.model.entity.MediaCreatorThumb;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.vo.MediaCreatorVO;
import com.quotawish.leaveword.model.vo.UserVO;
import com.quotawish.leaveword.service.MediaCreatorService;
import com.quotawish.leaveword.service.UserService;
import com.quotawish.leaveword.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    /**
     * 校验数据
     *
     * @param media_creator
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validMediaCreator(MediaCreator media_creator, boolean add) {
        ThrowUtils.throwIf(media_creator == null, ErrorCode.PARAMS_ERROR);
        // todo 从对象中取值
        String title = media_creator.getTitle();
        // 创建数据时，参数不能为空
        if (add) {
            // todo 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(title), ErrorCode.PARAMS_ERROR);
        }
        // 修改数据时，有参数则校验
        // todo 补充校验规则
        if (StringUtils.isNotBlank(title)) {
            ThrowUtils.throwIf(title.length() > 80, ErrorCode.PARAMS_ERROR, "标题过长");
        }
    }

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
        // todo 从对象中取值
        Long id = media_creatorQueryRequest.getId();
        Long notId = media_creatorQueryRequest.getNotId();
        String title = media_creatorQueryRequest.getTitle();
        String content = media_creatorQueryRequest.getContent();
        String searchText = media_creatorQueryRequest.getSearchText();
        String sortField = media_creatorQueryRequest.getSortField();
        String sortOrder = media_creatorQueryRequest.getSortOrder();
        List<String> tagList = media_creatorQueryRequest.getTags();
        Long userId = media_creatorQueryRequest.getUserId();
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

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = media_creator.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        media_creatorVO.setUser(userVO);
        // 2. 已登录，获取用户点赞、收藏状态
        long media_creatorId = media_creator.getId();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            // 获取点赞
            QueryWrapper<MediaCreatorThumb> media_creatorThumbQueryWrapper = new QueryWrapper<>();
            media_creatorThumbQueryWrapper.in("media_creatorId", media_creatorId);
            media_creatorThumbQueryWrapper.eq("userId", loginUser.getId());
            MediaCreatorThumb media_creatorThumb = media_creatorThumbMapper.selectOne(media_creatorThumbQueryWrapper);
            media_creatorVO.setHasThumb(media_creatorThumb != null);
            // 获取收藏
            QueryWrapper<MediaCreatorFavour> media_creatorFavourQueryWrapper = new QueryWrapper<>();
            media_creatorFavourQueryWrapper.in("media_creatorId", media_creatorId);
            media_creatorFavourQueryWrapper.eq("userId", loginUser.getId());
            MediaCreatorFavour media_creatorFavour = media_creatorFavourMapper.selectOne(media_creatorFavourQueryWrapper);
            media_creatorVO.setHasFavour(media_creatorFavour != null);
        }
        // endregion

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

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = media_creatorList.stream().map(MediaCreator::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 2. 已登录，获取用户点赞、收藏状态
        Map<Long, Boolean> media_creatorIdHasThumbMap = new HashMap<>();
        Map<Long, Boolean> media_creatorIdHasFavourMap = new HashMap<>();
        User loginUser = userService.getLoginUserPermitNull(request);
        if (loginUser != null) {
            Set<Long> media_creatorIdSet = media_creatorList.stream().map(MediaCreator::getId).collect(Collectors.toSet());
            loginUser = userService.getLoginUser(request);
            // 获取点赞
            QueryWrapper<MediaCreatorThumb> media_creatorThumbQueryWrapper = new QueryWrapper<>();
            media_creatorThumbQueryWrapper.in("media_creatorId", media_creatorIdSet);
            media_creatorThumbQueryWrapper.eq("userId", loginUser.getId());
            List<MediaCreatorThumb> media_creatorMediaCreatorThumbList = media_creatorThumbMapper.selectList(media_creatorThumbQueryWrapper);
            media_creatorMediaCreatorThumbList.forEach(media_creatorMediaCreatorThumb -> media_creatorIdHasThumbMap.put(media_creatorMediaCreatorThumb.getMediaCreatorId(), true));
            // 获取收藏
            QueryWrapper<MediaCreatorFavour> media_creatorFavourQueryWrapper = new QueryWrapper<>();
            media_creatorFavourQueryWrapper.in("media_creatorId", media_creatorIdSet);
            media_creatorFavourQueryWrapper.eq("userId", loginUser.getId());
            List<MediaCreatorFavour> media_creatorFavourList = media_creatorFavourMapper.selectList(media_creatorFavourQueryWrapper);
            media_creatorFavourList.forEach(media_creatorFavour -> media_creatorIdHasFavourMap.put(media_creatorFavour.getMediaCreatorId(), true));
        }
        // 填充信息
        media_creatorVOList.forEach(media_creatorVO -> {
            Long userId = media_creatorVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            media_creatorVO.setUser(userService.getUserVO(user));
            media_creatorVO.setHasThumb(media_creatorIdHasThumbMap.getOrDefault(media_creatorVO.getId(), false));
            media_creatorVO.setHasFavour(media_creatorIdHasFavourMap.getOrDefault(media_creatorVO.getId(), false));
        });
        // endregion

        media_creatorVOPage.setRecords(media_creatorVOList);
        return media_creatorVOPage;
    }

}
