package com.quotawish.leaveword.model.entity.english.word;

import lombok.Data;

import java.util.Map;

@Data
public class WordPronounce {

    /**
     * 发音内容
     */
    private String content;

    /**
     * 发音地址
     */
    private String audio;

    /**
     * 发音解释
     */
    private String description;

    /**
     * 额外信息
     */
    private Map<String, String> info;
}