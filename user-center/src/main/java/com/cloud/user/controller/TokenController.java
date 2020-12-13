package com.cloud.user.controller;

import com.cloud.model.user.AppUser;
import com.cloud.model.user.Result;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.user.feign.Oauth2Client;
import com.cloud.user.service.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 登陆、刷新token、退出
 *
 * @author 关键
 */
@Slf4j
@RestController
@Api("登录控制")
public class TokenController {

    @Autowired
    private Oauth2Client oauth2Client;
    @Autowired
    private AppUserService appUserService;

    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "系统登录",httpMethod = "POST")
    @PostMapping("/sys/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",paramType = "query"),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "query")
    })
    public Map<String, Object> login(String username, String password) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "system");
        parameters.put("client_secret", "system");
        parameters.put(OAuth2Utils.SCOPE, "app");
//		parameters.put("username", username);
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", username + "|" + CredentialType.USERNAME.name());
        parameters.put("password", password);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        return tokenInfo;
    }

//    /**
//     * 短信登录
//     *
//     * @param phone
//     * @param key
//     * @param code
//     * @return
//     */
//    @PostMapping("/sys/login-sms")
//    public Map<String, Object> smsLogin(String phone, String key, String code) {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
//        parameters.put(OAuth2Utils.CLIENT_ID, "system");
//        parameters.put("client_secret", "system");
//        parameters.put(OAuth2Utils.SCOPE, "app");
//        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了校验短信验证码，我们也拼上code等
//        parameters.put("username", phone + "|" + CredentialType.PHONE.name() + "|" + key + "|" + code + "|"
//                + DigestUtils.md5Hex(key + code));
//        // 短信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
//        parameters.put("password", phone);
//
//        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);
//
//        return tokenInfo;
//    }
//
//    /**
//     * 微信登录
//     *
//     * @return
//     */
//    @PostMapping("/sys/login-wechat")
//    public Map<String, Object> smsLogin(String openid, String tempCode) {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
//        parameters.put(OAuth2Utils.CLIENT_ID, "system");
//        parameters.put("client_secret", "system");
//        parameters.put(OAuth2Utils.SCOPE, "app");
//        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了服务端校验，我们也拼上tempCode
//        parameters.put("username", openid + "|" + CredentialType.WECHAT_OPENID.name() + "|" + tempCode);
//        // 微信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
//        parameters.put("password", tempCode);
//
//        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);
//
//        return tokenInfo;
//    }

    /**
     * 系统刷新refresh_token
     *
     * @param refresh_token
     * @return
     */
    @ApiOperation(value = "刷新token",httpMethod = "POST")
    @PostMapping("/sys/refresh_token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refresh_token",value = "刷新token",paramType = "query"),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public Map<String, Object> refresh_token(String refresh_token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, "system");
        parameters.put("client_secret", "system");
        parameters.put(OAuth2Utils.SCOPE, "app");
        parameters.put("refresh_token", refresh_token);

        return oauth2Client.postAccessToken(parameters);
    }

    /**
     * 退出
     *
     * @param access_token
     */
    @ApiOperation(value = "系统登出",httpMethod = "GET")
    @GetMapping("/sys/logout")
    @ApiImplicitParam(name = "access_token",value = "token",required = true)
    public void logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNoneBlank(token)) {
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        oauth2Client.removeToken(access_token);
    }
}
