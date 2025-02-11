package com.quotawish.leaveword.model.entity.english.word;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.net.URL;
import java.util.ArrayList;
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

    /**
     * 进行的修正，对数据进行一些必要的修正。
     * 此项会返回一个新的修复副本 而不是直接改动本身
     */
    public static WordContent structFixing(WordContent source) {
        WordContent fixed = new WordContent();
        BeanUtils.copyProperties(source, fixed);

        fixed.setImg(fixImg(fixed.getImg()));

        fixed.setTransform(fixTransform(fixed.getTransform()));

        fixed.setTranslation(fixTranslation(fixed.getTranslation()));

        fixed.setExamplePhrases(fixExamplePhrases(fixed.getExamplePhrases()));

        return fixed;
    }

    /**
     * 对图片的修正
     * 1.如果图片存在空字符串 或者不符合 https 的地址 会自动去除
     * 2.如果图片的hostname不合法 也会去除（比如 example.com / localhost / 127.0.0.1）
     * 3.如果图片的hostname合法 但是没有https协议 会自动加上
     */
    public static List<String> fixImg(List<String> img) {
        List<String> newImg = new ArrayList<>(img);
        if (img != null) {
            newImg.removeIf(item -> item == null || !item.startsWith("https://") || !item.contains("."));
//            img.forEach(item -> {
//                if (!item.startsWith("https://")) {
//                    item = "https://" + item;
//                }
//            });
            // 把每一个img都parse为url
            img.forEach(item -> {
                try {
                    URL url = new URL(item);
                    if (url.getHost().contains("example.com") || url.getHost().contains("localhost") || url.getHost().contains("127.0.0.1")) {
                        newImg.remove(item);
                    }
                } catch (Exception e) {
                    newImg.remove(item);
                }
            });
        }

        return newImg;
    }

    /**
     * 对transform的修正
     * 1.如果example中的音频数据为空 会自动通过sentence导入数据内容
     */
    public static List<WordTransform> fixTransform(List<WordTransform> transform) {

        transform.forEach(item -> {
            WordExample example = item.getExample();

            if (StrUtil.isBlankIfStr(example.getSentence()) ) return;

            example.setAudio(fixPronounce(example.getAudio(), example.getSentence()));
        });

        return transform;
    }

    /**
     * 对translation的修正
     * 1.如果example中的音频数据为空 会自动通过sentence导入数据内容
     */
    public static List<WordTranslation> fixTranslation(List<WordTranslation> translation) {

        translation.forEach(item -> {
            WordExample example = item.getExample();

            if (StrUtil.isBlankIfStr(example.getSentence()) ) return;

            example.setAudio(fixPronounce(example.getAudio(), example.getSentence()));
        });

        return translation;
    }

    /**
     * 对examplePhrases的修正
     * 1. 如果example中的音频数据为空 会自动通过sentence导入数据内容
     * 2. 如果某一个example的type不是PHRASE会自动修正
     */
    public static List<WordExample> fixExamplePhrases(List<WordExample> examplePhrases) {

        examplePhrases.forEach(item -> {
            WordExample.WordExampleType type = item.getType();
            if (type != WordExample.WordExampleType.PHRASE) {
                item.setType(WordExample.WordExampleType.PHRASE);
            }

            if (StrUtil.isBlankIfStr(item.getSentence()) ) return;

            item.setAudio(fixPronounce(item.getAudio(), item.getSentence()));
        });

        return examplePhrases;
    }

    /**
     * 对WordPronounce的修正 会自动根据传入的内容导入（首选audio.content 如果没有选择initialValue)
     */
    public static WordPronounce fixPronounce(WordPronounce pronounce, String initialValue) {
        if (pronounce != null) {
            if (pronounce.getAudio() == null || pronounce.getAudio().isEmpty()) {
                // https://dict.youdao.com/dictvoice
                String content = initialValue;
                if (pronounce.getContent() != null) {
                    content = pronounce.getContent();
                }
                pronounce.setAudio("https://dict.youdao.com/dictvoice?audio=" + content.replace(" ", "+"));
            }
        }
        return pronounce;
    }
}