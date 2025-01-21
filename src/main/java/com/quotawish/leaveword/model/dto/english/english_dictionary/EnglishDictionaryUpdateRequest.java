package com.quotawish.leaveword.model.dto.english.english_dictionary;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

/**
 * 更新英语词典请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishDictionaryUpdateRequest implements Serializable {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotBlank(message = "词典名称不能为空")
    @Size(max = 255, message = "词典名称长度不能超过255个字符")
    private String name;

    @Size(max = 500, message = "词典描述长度不能超过500个字符")
    private String description;

    @NotBlank(message = "词典图片URL不能为空")
    @Size(max = 255, message = "词典图片URL长度不能超过255个字符")
    private String image_url;

    @Size(max = 255, message = "作者长度不能超过255个字符")
    private String author;

    @Size(max = 20, message = "ISBN编号长度不能超过20个字符")
    private String isbn;

    @Size(max = 20, message = "出版日期长度不能超过20个字符")
    private String publication_date;

    @Size(max = 255, message = "出版社长度不能超过255个字符")
    private String publisher;

    private static final long serialVersionUID = 1L;
}