package com.quotawish.leaveword.model.dto.english.english_dictionary;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 导入英语词典请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishDictionaryImportRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 词典描述
     */
    @NotNull(message = "对应单词列表不能为空")
    @Size(min = 1, max = 10000, message = "单词列表大小必须在1到10000之间")
    private List<String> description;

    private static final long serialVersionUID = 1L;
}