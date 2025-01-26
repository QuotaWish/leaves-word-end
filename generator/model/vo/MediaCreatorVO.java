package com.quotawish.leaveword.model.vo;

import cn.hutool.json.JSONUtil;
import com.quotawish.leaveword.model.entity.MediaCreator;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 媒体创建表视图
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class MediaCreatorVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 创建用户信息
     */
    private UserVO user;

    /**
     * 封装类转对象
     *
     * @param media_creatorVO
     * @return
     */
    public static MediaCreator voToObj(MediaCreatorVO media_creatorVO) {
        if (media_creatorVO == null) {
            return null;
        }
        MediaCreator media_creator = new MediaCreator();
        BeanUtils.copyProperties(media_creatorVO, media_creator);
        List<String> tagList = media_creatorVO.getTagList();
        media_creator.setTags(JSONUtil.toJsonStr(tagList));
        return media_creator;
    }

    /**
     * 对象转封装类
     *
     * @param media_creator
     * @return
     */
    public static MediaCreatorVO objToVo(MediaCreator media_creator) {
        if (media_creator == null) {
            return null;
        }
        MediaCreatorVO media_creatorVO = new MediaCreatorVO();
        BeanUtils.copyProperties(media_creator, media_creatorVO);
        media_creatorVO.setTagList(JSONUtil.toList(media_creator.getTags(), String.class));
        return media_creatorVO;
    }
}
