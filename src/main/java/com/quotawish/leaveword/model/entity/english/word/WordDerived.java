package com.quotawish.leaveword.model.entity.english.word;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 派生词类，用于存储单词的各种派生形式及其相关信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDerived {

    /**
     * 派生类型，包括同义词，反义词，同上位词，同下位词，同整体词，同部分词，相似词，相关词，意义易混淆，发音易混淆等。
     */
    private DerivationType type;

    /**
     * 变换内容，即变换后的单词形式。
     */
    private String content;

    /**
     * 变换相关的数据，以键值对形式存储。
     */
    private Map<String, String> data;

    public enum DerivationType {
        SYNONYM("同义词", "与原词意义相同或相近的词"),
        ANTONYM("反义词", "与原词意义相对的词"),
        HYPERNYM("上位词", "比原词更广泛的词"),
        HYPONYM("下位词", "比原词更具体的词"),
        HOLONYM("整体词", "包含原词的整体"),
        MERONYM("部分词", "原词的一部分"),
        COHYPERNYM("同上位词", "与原词有相同上位词的词"),
        COHYPONYM("同下位词", "与原词有相同下位词的词"),
        COHYMONYM("同整体词", "与原词有相同整体的词"),
        COMERONYM("同部分词", "与原词有相同部分的词"),
        SIMILAR("相似词", "与原词在某些方面相似的词"),
        RELATED("相关词", "与原词在某些方面相关的词"),
        MEANING_CONFUSABLE("意义易混淆", "与原词在意义上容易混淆的词"),
        PRONUNCIATION_CONFUSABLE("发音易混淆", "与原词在发音上容易混淆的词");

        private final String name;
        private final String description;

        DerivationType(String name, String description) {
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