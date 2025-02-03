package com.quotawish.leaveword.model.dto.category;

import com.quotawish.leaveword.common.PageRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 创建分类请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class CategoryRelativeRequest implements Serializable {

    /**
     * id
     */
    @NotNull(message = "关联的词典不能为空")
    private Long dict_id;

    /**
     * id
     */
    @Size(min = 1, max = 100,message = "关联的分类不能为空")
    private int[] category_ids;

    private static final long serialVersionUID = 1L;
}