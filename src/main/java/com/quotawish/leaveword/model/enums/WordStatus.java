package com.quotawish.leaveword.model.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WordStatus {
    UNKNOWN("未知"),
    CREATED("已创建"),
    UPLOADING("上传中"),
    UPLOADED("已上传"),
    IMPORTING("导入中"),
    EXPORTING("导出中"),
    EXPORTED("已导出"),
    PROCESSING("处理中"),
    PROCESSED("已处理"),
    REVIEWING("审核中"),
    APPROVED("已审核通过"),
    REJECTED("被驳回"),
    FAILED("失败"),
    DATA_FORMAT_ERROR("数据格式校验不通过"),
    DELETED("已删除"),
    IN_QUEUE("队列中"),
    PUBLISHED("已发布"),
    UNPUBLISHED("未发布");

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
            if (status.getDescription().equals(value)) {
                return status;
            }
        }
        return WordStatus.UNKNOWN;
    }
}