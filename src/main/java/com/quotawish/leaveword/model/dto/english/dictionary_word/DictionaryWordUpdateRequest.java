package com.quotawish.leaveword.model.dto.english.dictionary_word;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 编辑词典单词表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class DictionaryWordUpdateRequest implements Serializable {

    @NotNull(message = "词典ID不能为空")
    private Long dictionary_id;

    @NotNull(message = "单词ID不能为空")
    private Long word_id;

    private static final long serialVersionUID = 1L;
}