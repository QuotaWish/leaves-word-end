package com.quotawish.leaveword.model.entity.english;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 书籍分类关系表
 */
@Data
public class DictionaryCategory {
    @NotNull(message = "不能为null")
    private Integer id;

    @NotNull(message = "不能为null")
    private Integer dictionaryId;

    @NotNull(message = "不能为null")
    private Integer categoryId;

    /**
    * 分类内排序
    */
    private Integer sortOrder;

    private Date createdAt;
}