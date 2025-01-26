package com.quotawish.leaveword.model.dto.audio;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.quotawish.leaveword.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 查询音频文件表请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AudioFileQueryRequest extends PageRequest implements Serializable {

    /**
     * 音频文件的唯一标识符
     */
    private Long id;

    /**
     * 音频文件的标题。
     */
    @NotNull(message = "搜索条件不能为空")
    private String name;

    /**
     * 音频文件的内容。
     */
    private String content;
}