package com.quotawish.leaveword.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类，用于存储用户的基本信息。
 */
@TableName(value = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    /**
     * 用户唯一标识符，自增ID。
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户账号，用于登录系统。
     */
    private String userAccount;

    /**
     * 用户密码，用于验证用户身份。
     */
    private String userPassword;

    /**
     * 第三方开放平台的唯一标识符。
     */
    private String unionId;

    /**
     * 微信公众号的唯一标识符。
     */
    private String mpOpenId;

    /**
     * 用户昵称，用于显示在用户界面上。
     */
    private String userName;

    /**
     * 用户头像的URL地址。
     */
    private String userAvatar;

    /**
     * 用户简介，用于描述用户的基本信息。
     */
    private String userProfile;

    /**
     * 用户角色，包括user（普通用户）、admin（管理员）、ban（被封禁用户）。
     */
    private String userRole;

    /**
     * 用户创建时间。
     */
    private Date createTime;

    /**
     * 用户信息最后更新时间。
     */
    private Date updateTime;

    /**
     * 逻辑删除标识符，0表示未删除，1表示已删除。
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}