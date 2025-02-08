package com.quotawish.leaveword.model.dto.english.english_word;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 获取英语单词ID（批量）
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishWordGetBatchRequest implements Serializable {

    /**
     * 要添加的单词列表
     */
    @NotNull(message = "单词列表不能为空")
    private List<String> words;

    private static final long serialVersionUID = 1L;
}