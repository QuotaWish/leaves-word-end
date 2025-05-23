package com.quotawish.leaveword.model.dto.english.english_word.change_log;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑英语单词变更记录请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishWordChangeLogEditRequest implements Serializable {

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
     * 标签列表
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}