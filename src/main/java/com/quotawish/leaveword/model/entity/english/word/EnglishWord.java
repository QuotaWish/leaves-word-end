package com.quotawish.leaveword.model.entity.english.word;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 单词表
 * @TableName english_word
 */
@TableName(value ="english_word")
@Data
public class EnglishWord implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 单词头部
     */
    private String word_head;

    /**
     * 单词内容
     */
    private String info;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer is_delete;

    /**
     * 审核状态
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public WordContent transformInfo() {
        return JSONUtil.toBean(this.info, WordContent.class);
    }

    public static boolean isStandardFormat(String json) {
        JSONObject parsedData = JSONUtil.parseObj(json);
//        WordContent wordContent = new WordContent();

        for (String key : parsedData.keySet()) {
            Object value = parsedData.get(key);

            if (!checkType(key, value)) {
                throw new IllegalArgumentException("属性 " + key + " 的值不符合类型要求");
            }

            // TODO - 赋值
        }

        return true;// parsedData;
    }


    private static boolean checkType(String key, Object value) {
        switch (key) {
            case "title":
            case "content":
            case "author":
            case "remember":
            case "story":
            case "backgroundStory":
                return value instanceof String;
            case "createdAt":
                try {
                    new Date(value.toString());
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "britishPronounce":
            case "americanPronounce":
                return value instanceof Map;
            case "derived":
            case "img":
            case "transform":
            case "translation":
            case "examplePhrases":
            case "parts":
                return value instanceof List;
            default:
                return false;
        }
    }

}