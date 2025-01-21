package com.quotawish.leaveword.model.vo.english;

import com.quotawish.leaveword.model.entity.english.EnglishDictionary;
import com.quotawish.leaveword.model.vo.UserVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 英语词典视图
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishDictionaryVO implements Serializable {

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
     * @param english_dictionaryVO
     * @return
     */
    public static EnglishDictionary voToObj(EnglishDictionaryVO english_dictionaryVO) {
        if (english_dictionaryVO == null) {
            return null;
        }
        EnglishDictionary english_dictionary = new EnglishDictionary();
        BeanUtils.copyProperties(english_dictionaryVO, english_dictionary);
        return english_dictionary;
    }

    /**
     * 对象转封装类
     *
     * @param english_dictionary
     * @return
     */
    public static EnglishDictionaryVO objToVo(EnglishDictionary english_dictionary) {
        if (english_dictionary == null) {
            return null;
        }
        EnglishDictionaryVO english_dictionaryVO = new EnglishDictionaryVO();
        BeanUtils.copyProperties(english_dictionary, english_dictionaryVO);
        return english_dictionaryVO;
    }
}
