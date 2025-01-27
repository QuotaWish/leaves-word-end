package com.quotawish.leaveword.model.dto.english.dictionary_word;

import com.quotawish.leaveword.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 查询词典单词表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictionaryWordQueryRequest extends PageRequest implements Serializable {

    @NotNull(message = "词典ID不能为空")
    private Long dictionary_id;

    @NotNull(message = "单词ID不能为空")
    private Long word_id;

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;


    private static final long serialVersionUID = 1L;
}