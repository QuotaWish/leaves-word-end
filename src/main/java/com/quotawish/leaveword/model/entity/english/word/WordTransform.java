package com.quotawish.leaveword.model.entity.english.word;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 变换实体类，用于存储单词的各种变换形式及其相关信息。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordTransform {

    /**
     * 变换唯一标识符，自增ID。
     */
    private Long id;

    /**
     * 变换类型，包括单数形式、复数形式等。
     */
    private TransformType type;

    /**
     * 变换内容，即变换后的单词形式。
     */
    private String content;

    /**
     * 变换相关的数据，以键值对形式存储。
     */
    private Map<String, String> data;

    /**
     * 变换对应的例句。
     */
    private WordExample example;

    public enum TransformType {
        NONE("无变换形式", "单词本身"),
        SINGULAR("单数形式", "单词的单数形式"),
        PLURAL("复数形式", "单词的复数形式"),
        ING_FORM("进行时形式", "单词的进行时形式"),
        ED_FORM("过去式形式", "单词的过去式形式"),
        DONE_FORM("完成形式", "单词的完成形式"),
        DONE_PARTICIPLE("完成分词形式", "单词的完成分词形式"),
        COMPARATIVE("比较级形式", "形容词或副词的比较级形式"),
        SUPERLATIVE("最高级形式", "形容词或副词的最高级形式"),
        POSSESSIVE("所有格形式", "名词的所有格形式"),
        TO_INF("不定式形式", "动词的不定式形式"),
        TO_ING("不定式进行时形式", "动词的不定式进行时形式"),
        TO_PERFECT("不定式完成时形式", "动词的不定式完成时形式"),
        TO_PERFECT_CONTINUOUS("不定式完成进行时形式", "动词的不定式完成进行时形式"),
        PASSIVE_VOICE("被动语态形式", "动词的被动语态形式"),
        CONDITIONAL("条件式形式", "动词的条件式形式"),
        SUBJUNCTIVE("虚拟语气形式", "动词的虚拟语气形式");

        private final String name;
        private final String description;

        TransformType(String name, String description) {
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