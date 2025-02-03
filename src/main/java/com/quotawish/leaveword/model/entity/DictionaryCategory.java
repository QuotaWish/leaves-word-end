package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 书籍分类关系表
 */
@Data
@TableName(value = "dictionary_category")
public class DictionaryCategory {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    @TableField(value = "dictionary_id")
    @NotNull(message = "不能为null")
    private Long dictionaryId;

    @TableField(value = "category_id")
    @NotNull(message = "不能为null")
    private Integer categoryId;

    /**
     * 分类内排序
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

    @TableField(value = "created_at")
    private Date createdAt;
}