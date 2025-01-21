package com.quotawish.leaveword.model.entity.english.word;

import lombok.Data;

import java.util.List;

@Data
public class WordContent {

    /**
     * 英式英语发音
     */
    private WordPronounce britishPronounce;

    /**
     * 美式英语发音
     */
    private WordPronounce americanPronounce;

    /**
     * 派生词列表，包含派生词的详细信息。
     */
    private List<WordDerived> derived;

    /**
     * 图片列表，包含单词的图片地址。
     */
    private List<String> img;

    /**
     * 缩略图地址
     */
//    private String thumbnail;

    /**
     * 记忆辅助信息，帮助用户记忆单词。
     */
    private String remember;

    /**
     * 小故事，包含与单词相关的小故事。
     */
    private String story;

    /**
     * 变形列表，包含单词的不同变形形式。
     */
    private List<WordTransform> transform;

    /**
     * 翻译信息，包含单词的翻译。
     */
    private List<WordTranslation> translation;

    /**
     * 短语例句
     */
    private List<WordExample> examplePhrases;

    /**
     * 词根列表，包含单词的词根信息。
     */
    private List<WordAffixPart> parts;

    /**
     * 背景故事，包含单词的背景故事。
     */
    private String backgroundStory;
}