package com.quotawish.leaveword.model.dto.english.status_change;

import com.quotawish.leaveword.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询音频文件表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EnglishWordStatusChangeQueryRequest extends PageRequest implements Serializable {

    /**
     * 音频文件的唯一标识符
     */
    private Long id;

    /**
     * 变更记录文件的评论。
     */
    private String comment;
}