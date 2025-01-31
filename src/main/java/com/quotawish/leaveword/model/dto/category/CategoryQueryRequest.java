package com.quotawish.leaveword.model.dto.category;

import com.quotawish.leaveword.common.PageRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 创建分类请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class CategoryQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;

    /**
     * 搜索词
     */
    private String searchText;

    @Length(max = 255, message = "分类名称长度不能超过255个字符")
    private String name;

    /**
     * 分类描述
     */
    @Length(max = 500, message = "分类描述长度不能超过500个字符")
    private String description;

    private static final long serialVersionUID = 1L;
}