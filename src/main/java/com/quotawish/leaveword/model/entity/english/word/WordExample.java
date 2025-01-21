package com.quotawish.leaveword.model.entity.english.word;

import lombok.Data;

@Data
public class WordExample {

    /**
     * 例句类型，例如：句子、短语等。
     */
    private WordExampleType type;

    /**
     * 附加信息。
     */
    private String addon;

    /**
     * 高亮部分。
     */
    private String highlight;

    /**
     * 完整的例句。
     */
    private String sentence;

    /**
     * 例句的翻译。
     */
    private String translation;

    /**
     * 例句的音频文件。
     */
    private WordPronounce audio;

    /**
     * 例句类型枚举类型。
     */
    public enum WordExampleType {
        SENTENCE("句子", "完整的句子"),
        PHRASE("短语", "句子的一部分");

        private final String name;
        private final String description;

        WordExampleType(String name, String description) {
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