package com.quotawish.leaveword.model.enums;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AudioFileStatus {
    UNKNOWN("未知"),
    UPLOADING("上传中"),
    UPLOADED("已上传"),
    PROCESSING("处理中"),
    PROCESSED("已处理"),
    FAILED("失败"),
    DELETED("已删除"),
    SYNTHESIZING("音频合成中"),
    IN_QUEUE("队列中");

    private final String description;

    AudioFileStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static @NotNull List<String> getValues() {
        return Arrays.stream(values()).map(AudioFileStatus::getDescription).collect(Collectors.toList());
    }

    public static AudioFileStatus getEnumByValue(String value) {
        for (AudioFileStatus status : values()) {
            if (status.getDescription().equals(value)) {
                return status;
            }
        }
        return null; // 修改返回值为null，因为UNKNOWN未定义
    }
}
