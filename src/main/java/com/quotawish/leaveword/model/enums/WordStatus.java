package com.quotawish.leaveword.model.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WordStatus {
    UNKNOWN("未知"),
    DRAFT("草稿"),
    CREATED("已创建"),
    PROCESSING("处理中"),
    SUPPLYING("扩充中"),
    SUPPLIED("已扩充"),
    STRUCTURE_FIXING("结构修复中"),
    STRUCTURED("已结构化"),
    IMAGING("生成图片中"),
    PROCESSED("已处理"),
    WAIT_FOR_AI_REVIEW("等待审核"),

    REVIEWING("审核中"),
    DATA_FORMAT_ERROR("数据格式校验不通过"),
    REJECTED("被驳回"),
    FAILED("失败"),
    PUBLISHED("已发布"),
    UNPUBLISHED("未发布"),

    APPROVED("已审核通过"),
    UPLOADED("已上传"),
    UPLOADING("上传中"),
    IMPORTING("导入中"),
    EXPORTING("导出中"),
    EXPORTED("已导出"),
    DELETED("已删除"),
    IN_QUEUE("队列中");

    // 整体逻辑是：
    // 1.用户创建的单词是未知 因为创建单词的时候不能放信息 所以是已创建
    // 2.批量导入的单词是 ①如果数据正确就是已处理 ②如果数据不正确就是数据格式校验不通过 ③如果没有数据就是未知
    // 3.一般来讲 导入的单词都是未知 因为数据不正确
    // 4.AI会自动将未知单词拿出来处理：AI自动将状态变更为已处理
    // 5.单词处理分为扩充和加图片 - 扩充后变成生成图片中 生成图片中->变成已处理
    // 5.AI会自动将已处理的单词拿出来审阅：AI自动将状态变更为UPLOADED（已上传到系统）通过或者被驳回
    // 6.已上传到系统的单词需要人工手动审阅 合格后会变成已通过 否则会变成被驳回
    // 7.已通过的单词会展示在词典的页面
    // 8.用户需要手动发布单词 - 已发布的单词会在训练数据中使用

    private final String description;

    WordStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static @NotNull List<String> getValues() {
        return Arrays.stream(values()).map(WordStatus::getDescription).collect(Collectors.toList());
    }

    public static WordStatus getEnumByValue(String value) {
        for (WordStatus status : values()) {
            if (status.getDescription().equals(value) || status.name().equals(value)) {
                return status;
            }
        }
        return WordStatus.UNKNOWN;
    }
}