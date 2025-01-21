package com.quotawish.leaveword.model.entity.english.word;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordTranslation implements Serializable {

    /**
     * 词性，例如：名词、动词等。
     */
    private WordType type;

    /**
     * 词性展示 （便于区分及物和不及物动词）
     */
    private String typeText;

    /**
     * 翻译结果。
     */
    private String translation;

    /**
     * 单词的定义或解释。
     */
    private String definition;

    /**
     * 单词的例句。
     */
    private WordExample example;

    /**
     * 单词的音标。
     */
    private String phonetic;

    /**
     * 单词的发音音频文件URL。
     */
    private WordPronounce audio;

    /**
     * 这个词性的频率 [0-1]
     */
    private double frequency;

    /**
     * 词性枚举类型。
     */
    public enum WordType {
        NOUN("名词", "表示人、事物、地点等的词"),
        VERB("动词", "表示动作、存在或状态的词"),
        ADJECTIVE("形容词", "用来修饰名词或代词的词"),
        ADVERB("副词", "用来修饰动词、形容词或其他副词的词"),
        PRONOUN("代词", "代替名词或名词短语的词"),
        PREPOSITION("介词", "表示名词或其他词与句子其他部分关系的词"),
        CONJUNCTION("连词", "连接词、短语或句子的词"),
        INTERJECTION("感叹词", "表示强烈感情或惊讶的词");

        private final String name;
        private final String description;

        WordType(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}