package com.quotawish.leaveword.model.entity.english.word;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 单词部分实体类，用于存储单词的各种部分形式及其相关信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordAffixPart {

    /**
     * 部分类型，包括前缀，后缀，中缀，缩略词，词根，词干等。
     */
    private WordAffixType type;

    /**
     * 部分内容，即部分后的单词。
     */
    private String content;

    /**
     *  部分关的数据，以键值对形式存储。
     */
    private Map<String, String> data;

    /**
     * 部分对应的解释。
     */
    private String description;

    public enum WordAffixType {
        NONE("无变换形式", "单词本身"),
        PREFIX("前缀", "添加在单词前面的词素"),
        SUFFIX("后缀", "添加在单词后面的词素"),
        INFIX("中缀", "插入在单词中间的词素"),
        ACRONYM("缩略词", "由单词的首字母组成的缩写"),
        ROOT("词根", "单词的基本部分"),
        STEM("词干", "去除形态变化后的单词核心部分");

        private final String name;
        private final String description;

        WordAffixType(String name, String description) {
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