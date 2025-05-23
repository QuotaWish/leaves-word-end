package com.quotawish.leaveword.model.dto.english.english_word;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑英语单词请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishWordEditRequest implements Serializable {

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
     * 当前状态
     */
    private String status;

    private static final long serialVersionUID = 1L;
}