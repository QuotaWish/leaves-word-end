package com.quotawish.leaveword.model.dto.english.english_word;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 更新英语单词请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishWordUpdateRequest implements Serializable {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 单词
     */
    @NotNull(message = "单词不能为空")
    @Length(max = 200, message = "单词长度不能超过200个字符")
    private String word_head;

    /**
     * 内容
     */
    @NotNull(message = "内容不能为空")
    @Length(max = 1024 * 128, message = "内容长度不能超过1024*128个字符")
    private String info;

    private static final long serialVersionUID = 1L;
}