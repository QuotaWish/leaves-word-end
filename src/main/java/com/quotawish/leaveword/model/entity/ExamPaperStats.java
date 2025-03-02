package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 试卷统计表
 */
@Data
@TableName(value = "exam_paper_stats")
public class ExamPaperStats {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 试卷ID
     */
    @TableField(value = "paper_id")
    @NotNull(message = "试卷ID不能为null")
    private Integer paperId;

    /**
     * 浏览次数
     */
    @TableField(value = "view_count")
    private Integer viewCount;

    /**
     * 下载次数
     */
    @TableField(value = "download_count")
    private Integer downloadCount;

    /**
     * 考试尝试次数
     */
    @TableField(value = "attempt_count")
    private Integer attemptCount;

    /**
     * 平均得分
     */
    @TableField(value = "average_score")
    private BigDecimal averageScore;

    /**
     * 统计更新时间
     */
    @TableField(value = "updated_at")
    private Date updatedAt;
}