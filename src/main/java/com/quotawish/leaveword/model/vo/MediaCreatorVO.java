package com.quotawish.leaveword.model.vo;

import java.util.Date;

import cn.hutool.json.JSONUtil;
import com.quotawish.leaveword.model.entity.audio.MediaCreator;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 媒体创作者视图对象
 */
@Data
public class MediaCreatorVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 关联的留言ID
     */
    private Long wordId;

    /**
     * 媒体类型
     */
    private String mediaType;

    /**
     * 媒体URL
     */
    private String mediaUrl;

    /**
     * 创建者ID
     */
    private Long creatorId;

    /**
     * 附加信息
     */
    private String info;

    /**
     * 创建时间
     */
    private Date createdAt;

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

        return media_creatorVO;
    }
}
