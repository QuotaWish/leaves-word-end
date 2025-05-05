package com.quotawish.leaveword.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.quotawish.leaveword.common.BaseResponse;
import com.quotawish.leaveword.common.ErrorCode;
import com.quotawish.leaveword.common.ResultUtils;
import com.quotawish.leaveword.exception.BusinessException;
import com.quotawish.leaveword.model.entity.User;
import com.quotawish.leaveword.model.entity.UserConfig;
import com.quotawish.leaveword.model.vo.UserConfigVO;
import com.quotawish.leaveword.service.UserService;
import com.quotawish.leaveword.service.impl.UserConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
* 用户配置表(user_config)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/user_config")
public class UserConfigController {
    
    /**
    * 服务对象
    */
    @Autowired
    private UserConfigImpl userConfigServiceImpl;
    
    @Resource
    private UserService userService;
    
    /**
     * 创建默认用户配置
     *
     * @param userId 用户ID
     * @return 创建的用户配置
     */
    private UserConfig createDefaultUserConfig(Long userId) {
        UserConfig userConfig = new UserConfig();
        userConfig.setUserId(userId);
        
        // 创建默认JSON配置，包含public和private两部分
        JSONObject configJson = new JSONObject();
        configJson.set("public", new JSONObject());  // 公开信息
        configJson.set("private", new JSONObject()); // 私有信息
        
        userConfig.setConfigJson(configJson.toString());
        userConfig.setUpdateTime(new Date());
        userConfigServiceImpl.save(userConfig);
        return userConfig;
    }
    
    /**
     * 将数据库中的JSON配置转换为UserConfigVO对象
     *
     * @param configJson 配置JSON字符串
     * @return UserConfigVO对象
     */
    private UserConfigVO convertToVO(String configJson) {
        UserConfigVO vo = new UserConfigVO();
        JSONObject jsonObj = JSONUtil.parseObj(configJson);
        
        // 获取public配置
        Map<String, Object> publicConfig = new HashMap<>();
        if (jsonObj.containsKey("public")) {
            JSONObject publicJson = jsonObj.getJSONObject("public");
            publicConfig = JSONUtil.toBean(publicJson, Map.class);
        }
        
        // 获取private配置
        Map<String, Object> privateConfig = new HashMap<>();
        if (jsonObj.containsKey("private")) {
            JSONObject privateJson = jsonObj.getJSONObject("private");
            privateConfig = JSONUtil.toBean(privateJson, Map.class);
        }
        
        vo.setPublicConfig(publicConfig);
        vo.setPrivateConfig(privateConfig);
        return vo;
    }
    
    /**
     * 将UserConfigVO对象转换为JSON字符串存储到数据库
     *
     * @param vo UserConfigVO对象
     * @return JSON字符串
     */
    private String convertToJson(UserConfigVO vo) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.set("public", vo.getPublicConfig());
        jsonObj.set("private", vo.getPrivateConfig());
        return jsonObj.toString();
    }
    
    /**
     * 获取当前登录用户的配置（包含完整配置）
     *
     * @param request HTTP请求
     * @return 完整的用户配置
     */
    @GetMapping("/get")
    @SaCheckLogin
    public BaseResponse<UserConfigVO> getCurrentUserConfig(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        
        // 查询用户配置
        UserConfig userConfig = userConfigServiceImpl.getById((Serializable)userId);
        
        // 如果配置不存在，创建默认配置
        if (userConfig == null) {
            userConfig = createDefaultUserConfig(userId);
        }
        
        // 转换为VO对象
        UserConfigVO vo = convertToVO(userConfig.getConfigJson());
        return ResultUtils.success(vo);
    }
    
    /**
     * 获取用户的公开配置（仅public部分）
     *
     * @param id 用户ID
     * @return 用户公开配置
     */
    @GetMapping("/public")
    public BaseResponse<UserConfigVO> getPublicUserConfig(@RequestParam("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户ID不合法");
        }
        
        // 查询用户配置
        UserConfig userConfig = userConfigServiceImpl.getById((Serializable)id);
        
        // 如果配置不存在，创建默认配置
        if (userConfig == null) {
            userConfig = createDefaultUserConfig(id);
        }
        
        // 转换为VO对象，但只返回public部分
        UserConfigVO vo = convertToVO(userConfig.getConfigJson());
        UserConfigVO publicVo = new UserConfigVO();
        publicVo.setPublicConfig(vo.getPublicConfig());
        publicVo.setPrivateConfig(new HashMap<>()); // 返回空的private配置
        
        return ResultUtils.success(publicVo);
    }
    
    /**
     * 更新当前登录用户的配置
     *
     * @param configVO 用户配置对象
     * @param request HTTP请求
     * @return 操作结果
     */
    @PostMapping("/update")
    @SaCheckLogin
    public BaseResponse<Boolean> updateCurrentUserConfig(@RequestBody UserConfigVO configVO, 
                                                      HttpServletRequest request) {
        // 参数校验
        if (configVO == null || configVO.getPublicConfig() == null || configVO.getPrivateConfig() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "配置参数不完整");
        }
        
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        
        // 查询用户现有配置
        UserConfig userConfig = userConfigServiceImpl.getById((Serializable)userId);
        
        // 将VO转换为JSON存储
        String configJson = convertToJson(configVO);
        
        // 配置不存在则创建新配置
        if (userConfig == null) {
            userConfig = new UserConfig();
            userConfig.setUserId(userId);
            userConfig.setConfigJson(configJson);
            userConfig.setUpdateTime(new Date());
            boolean result = userConfigServiceImpl.save(userConfig);
            return ResultUtils.success(result);
        }
        
        // 更新已有配置
        userConfig.setConfigJson(configJson);
        userConfig.setUpdateTime(new Date());
        boolean result = userConfigServiceImpl.updateById(userConfig);
        return ResultUtils.success(result);
    }
    
    /**
     * 通过ID查询单条数据（如果是自己查询则返回完整信息，否则只返回public部分）
     *
     * @param id 用户ID
     * @param request HTTP请求
     * @return 用户配置
     */
    @GetMapping("/select")
    public BaseResponse<UserConfigVO> selectOne(@RequestParam("id") Long id, HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户ID不合法");
        }
        
        // 判断是否为用户查询自己的配置
        boolean isSelf = false;
        try {
            User loginUser = userService.getLoginUser(request);
            isSelf = loginUser != null && loginUser.getId().equals(id);
        } catch (Exception e) {
            // 用户未登录，或其他异常，按非本人处理
            isSelf = false;
        }
        
        // 查询用户配置
        UserConfig userConfig = userConfigServiceImpl.getById((Serializable)id);
        
        // 如果配置不存在，创建默认配置
        if (userConfig == null) {
            userConfig = createDefaultUserConfig(id);
        }
        
        // 转换为VO对象
        UserConfigVO vo = convertToVO(userConfig.getConfigJson());
        
        // 根据查询者身份决定返回内容
        if (isSelf) {
            // 本人查询，返回完整配置
            return ResultUtils.success(vo);
        } else {
            // 他人查询，只返回public部分
            UserConfigVO publicVo = new UserConfigVO();
            publicVo.setPublicConfig(vo.getPublicConfig());
            publicVo.setPrivateConfig(new HashMap<>()); // 返回空的private配置
            return ResultUtils.success(publicVo);
        }
    }
}
