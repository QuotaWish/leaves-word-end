package com.quotawish.leaveword.model.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

/**
 * 用户配置视图对象
 */
@Data
public class UserConfigVO implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 公开配置信息
   */
  private Map<String, Object> publicConfig;

  /**
   * 私有配置信息
   */
  private Map<String, Object> privateConfig;

  /**
   * 创建一个默认的空配置
   */
  public static UserConfigVO createDefault() {
    UserConfigVO vo = new UserConfigVO();
    vo.setPublicConfig(new HashMap<>());
    vo.setPrivateConfig(new HashMap<>());
    return vo;
  }
}