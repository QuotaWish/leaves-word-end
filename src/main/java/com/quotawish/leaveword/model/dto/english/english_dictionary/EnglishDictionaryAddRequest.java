package com.quotawish.leaveword.model.dto.english.english_dictionary;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 创建英语词典请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class EnglishDictionaryAddRequest implements Serializable {

    /**
     * 词典名称
     */
    @NotBlank(message = "词典名称不能为空")
    @Length(max = 255, message = "词典名称长度不能超过255个字符")
    private String name;

    /**
     * 词典描述
     */
    @Length(max = 500, message = "词典描述长度不能超过500个字符")
    private String description;

    /**
     * 词典图片URL
     */
    @URL(message = "词典图片URL格式不正确")
    @Length(max = 255, message = "词典图片URL长度不能超过255个字符")
    private String image_url;

    /**
     * 作者
     */
//    @NotBlank(message = "作者不能为空")
    @Length(max = 255, message = "作者长度不能超过255个字符")
    private String author;

    /**
     * ISBN编号
     */
//    @NotBlank(message = "ISBN编号不能为空")
    @Length(max = 13, message = "ISBN编号长度不能超过13个字符")
    private String isbn;

    /**
     * 出版日期
     */
//    @NotBlank(message = "出版日期不能为空")
    @Length(max = 20, message = "出版日期长度不能超过20个字符")
    private String publication_date;

    /**
     * 出版社
     */
//    @NotBlank(message = "出版社不能为空")
    @Length(max = 255, message = "出版社长度不能超过255个字符")
    private String publisher;

    private static final long serialVersionUID = 1L;
}