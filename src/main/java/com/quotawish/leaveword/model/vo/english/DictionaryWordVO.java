package com.quotawish.leaveword.model.vo.english;

import cn.hutool.json.JSONUtil;
import com.quotawish.leaveword.model.entity.DictionaryWord;
import com.quotawish.leaveword.model.vo.UserVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 词典单词表视图
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class DictionaryWordVO implements Serializable {

    /**
     * id
     */
    private Long id;

    @NotNull(message = "词典ID不能为空")
    private Long dictionary_id;

    @NotNull(message = "单词ID不能为空")
    private Long word_id;

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
    public static DictionaryWord voToObj(DictionaryWordVO dictionary_wordVO) {
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
    public static DictionaryWordVO objToVo(DictionaryWord dictionary_word) {
        if (dictionary_word == null) {
            return null;
        }
        DictionaryWordVO dictionary_wordVO = new DictionaryWordVO();
        BeanUtils.copyProperties(dictionary_word, dictionary_wordVO);

        return dictionary_wordVO;
    }
}
