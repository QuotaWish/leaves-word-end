package com.quotawish.leaveword.model.vo.english;

import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.model.entity.english.word.EnglishWord;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 词典单词表视图
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class DictionaryWordWithWordVO implements Serializable {

    /**
     * id
     */
    private Long id;

    private Long dictionary_id;

    private Long word_id;

    private EnglishWord word;

    /**
     * 创建时间
     */
    private Date create_at;

    /**
     * 封装类转对象
     *
     * @param dictionary_wordVO
     * @return
     */
    public static DictionaryWord voToObj(DictionaryWordWithWordVO dictionary_wordVO) {
        if (dictionary_wordVO == null) {
            return null;
        }
        DictionaryWord dictionary_word = new DictionaryWord();
        BeanUtils.copyProperties(dictionary_wordVO, dictionary_word);

        return dictionary_word;
    }

    /**
     * 对象转封装类
     *
     * @param dictionary_word
     * @return
     */
    public static DictionaryWordWithWordVO objToVo(DictionaryWord dictionary_word) {
        if (dictionary_word == null) {
            return null;
        }
        DictionaryWordWithWordVO dictionary_wordVO = new DictionaryWordWithWordVO();
        BeanUtils.copyProperties(dictionary_word, dictionary_wordVO);

        return dictionary_wordVO;
    }
}
