package com.quotawish.leaveword.model.vo;

import cn.dev33.satoken.stp.SaTokenInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthUserVO implements Serializable {

    LoginUserVO user;

    SaTokenInfo token;

}
