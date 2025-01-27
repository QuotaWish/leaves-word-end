package com.quotawish.leaveword.common;

import lombok.Data;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    @NotNull(message = "对应记录ID不能为空")
    private Long id;

    private static final long serialVersionUID = 1L;
}