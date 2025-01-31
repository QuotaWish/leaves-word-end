package com.quotawish.leaveword.model.dto.english.english_word;

import com.quotawish.leaveword.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询英语单词请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EnglishWordQueryRequest extends PageRequest implements Serializable {

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

    /**
     * 当前状态
     */
    private String status;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}