package com.quotawish.leaveword.model.vo.english;

import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import com.quotawish.leaveword.model.vo.UserVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 英语单词视图
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishWordVO implements Serializable {

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
     * 当前状态
     */
    private String status;

    /**
     * 创建用户信息
     */
    private UserVO user;

    /**
     * 封装类转对象
     *
     * @param english_wordVO
     * @return
     */
    public static EnglishWord voToObj(EnglishWordVO english_wordVO) {
        if (english_wordVO == null) {
            return null;
        }
        EnglishWord english_word = new EnglishWord();
        BeanUtils.copyProperties(english_wordVO, english_word);
        return english_word;
    }

    /**
     * 对象转封装类
     *
     * @param english_word
     * @return
     */
    public static EnglishWordVO objToVo(EnglishWord english_word) {
        if (english_word == null) {
            return null;
        }
        EnglishWordVO english_wordVO = new EnglishWordVO();
        BeanUtils.copyProperties(english_word, english_wordVO);
        return english_wordVO;
    }
}
