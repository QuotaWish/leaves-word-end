package com.quotawish.leaveword.model.vo.english;

import com.quotawish.leaveword.model.entity.english.word.EnglishWordChangeLog;
import com.quotawish.leaveword.model.vo.UserVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 英语单词变更记录视图
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishWordChangeLogVO implements Serializable {

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
     * @param english_word_change_logVO
     * @return
     */
    public static EnglishWordChangeLog voToObj(EnglishWordChangeLogVO english_word_change_logVO) {
        if (english_word_change_logVO == null) {
            return null;
        }
        EnglishWordChangeLog english_word_change_log = new EnglishWordChangeLog();
        BeanUtils.copyProperties(english_word_change_logVO, english_word_change_log);
        return english_word_change_log;
    }

    /**
     * 对象转封装类
     *
     * @param english_word_change_log
     * @return
     */
    public static EnglishWordChangeLogVO objToVo(EnglishWordChangeLog english_word_change_log) {
        if (english_word_change_log == null) {
            return null;
        }
        EnglishWordChangeLogVO english_word_change_logVO = new EnglishWordChangeLogVO();
        BeanUtils.copyProperties(english_word_change_log, english_word_change_logVO);
        return english_word_change_logVO;
    }
}
