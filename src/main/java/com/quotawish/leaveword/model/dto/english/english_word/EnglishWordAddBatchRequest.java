package com.quotawish.leaveword.model.dto.english.english_word;

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
public class EnglishWordAddBatchRequest implements Serializable {

    /**
     * 要添加的单词列表
     */
    @NotNull(message = "单词和内容列表不能为空")
    private List<EnglishWordAddRequest> words;

    private static final long serialVersionUID = 1L;
}