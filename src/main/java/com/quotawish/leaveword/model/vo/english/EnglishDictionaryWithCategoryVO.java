package com.quotawish.leaveword.model.vo.english;

import com.quotawish.leaveword.model.entity.Category;
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
public class EnglishDictionaryWithCategoryVO implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 书籍名称
     */
    private String name;

    /**
     * 书籍描述
     */
    private String description;

    /**
     * 书籍图片URL
     */
    private String image_url;

    /**
     * 作者
     */
    private String author;

    /**
     * ISBN编号
     */
    private String isbn;

    /**
     * 出版日期
     */
    private String publication_date;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 标签列表
     */
//    private List<String> tagList;

    private List<Category> categoryList;

    /**
     * 封装类转对象
     *
     * @param english_dictionaryVO
     * @return
     */
    public static EnglishDictionary voToObj(EnglishDictionaryWithCategoryVO english_dictionaryVO) {
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
    public static EnglishDictionaryWithCategoryVO objToVo(EnglishDictionary english_dictionary, List<Category> categoryList) {
        if (english_dictionary == null) {
            return null;
        }
        EnglishDictionaryWithCategoryVO english_dictionaryVO = new EnglishDictionaryWithCategoryVO();
        BeanUtils.copyProperties(english_dictionary, english_dictionaryVO);

        english_dictionaryVO.setCategoryList(categoryList);
        english_dictionaryVO.setId(english_dictionary.getId());

        return english_dictionaryVO;
    }
}
