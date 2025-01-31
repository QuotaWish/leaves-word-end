package com.quotawish.leaveword.model.dto.english.english_word;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 对某个英语单词进行评分
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishWordScoreRequest implements Serializable {

    @NotNull(message = "对应的单词ID")
    private Long id;

    @NotNull(message = "评分")
    private Integer score;

    @NotNull(message = "AI评分")
    private Integer aiScore;

    @NotNull(message = "AI评分内容")
    private String aiContent;

    private static final long serialVersionUID = 1L;
}