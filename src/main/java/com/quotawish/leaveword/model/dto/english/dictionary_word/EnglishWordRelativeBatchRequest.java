package com.quotawish.leaveword.model.dto.english.dictionary_word;

import com.quotawish.leaveword.model.dto.english.english_word.EnglishWordAddRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 创建英语单词请求（批量）
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishWordRelativeBatchRequest implements Serializable {

    @NotNull(message = "词典ID不能为空")
    private Long dictionary_id;

    /**
     * 要添加的单词列表
     * 这里放的是单词的ID列表 可以用另外一个接口快速获取单词id表
     */
    @NotNull(message = "单词列表不能为空")
    private List<Long> words;

    private static final long serialVersionUID = 1L;
}