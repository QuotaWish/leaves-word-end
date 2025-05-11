package com.quotawish.leaveword.model.dto.user;

import com.quotawish.leaveword.model.enums.UserFieldEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号字段
     * @see UserFieldEnum#USER_ACCOUNT
     */
    private String userAccount;

    /**
     * 用户密码字段
     * @see UserFieldEnum#USER_PASSWORD
     */
    private String userPassword;

    /**
     * 平台标识，比如 app / web / cms
     */
    private String platform;

    /**
     * 设备类型，例如 pc / mobile / tablet
     */
    private String deviceType;

    /**
     * 客户端生成的设备唯一ID，标识某一设备（选填）
     */
    private String deviceId;
}
