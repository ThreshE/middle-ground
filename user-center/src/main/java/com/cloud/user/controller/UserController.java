package com.cloud.user.controller;

import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;
import com.cloud.user.feign.SmsClient;
import com.cloud.user.service.AppUserService;
import feign.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@Api("用户控制")
public class UserController {

    @Autowired
    private AppUserService appUserService;

    /**
     * 当前登录用户 LoginAppUser
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户",httpMethod = "GET")
    @GetMapping("/users/current")
    @ApiImplicitParam(name = "access_token",value = "token",required = true)
    public LoginAppUser getLoginAppUser() {
        return AppUserUtil.getLoginAppUser();
    }

    @ApiOperation(value = "通过用户名获取登录用户",httpMethod = "GET")
    @GetMapping(value = "/users-anon/internal", params = "username")
    @ApiImplicitParam(name = "username",value = "用户名")
    public LoginAppUser findByUsername(String username) {
        return appUserService.findByUsername(username);
    }

    /**
     * 用户查询
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users")
    @ApiOperation(value = "用户查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "用户参数,包含username,用户名；nickname,昵称;enabled，是否可用；type，用户类型",paramType = "query",dataTypeClass = Map.class),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public Page<AppUser> findUsers(@RequestParam Map<String, Object> params) {
        return appUserService.findUsers(params);
    }

    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users/{id}")
    @ApiOperation(value = "通过用户id查找用户",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",paramType = "path",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public AppUser findUserById(@PathVariable Long id) {
        return appUserService.findById(id);
    }

    /**
     * 添加用户,根据用户名注册
     *
     * @param appUser
     * @return
     */
    @PostMapping("/users-anon/register")
    @ApiOperation(value = "添加用户",httpMethod = "POST")
    @ApiImplicitParam(name = "appUser",value = "用户")
    public AppUser register(@RequestBody AppUser appUser) {
        // 用户名等信息的判断逻辑挪到service了
        appUserService.addAppUser(appUser);

        return appUser;
    }

    /**
     * 修改自己的个人信息
     *
     * @param appUser
     * @return
     */
    @LogAnnotation(module = LogModule.UPDATE_ME)
    @PutMapping("/users/me")
    @ApiOperation(value = "修改个人信息",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appUser",value = "用户"),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public AppUser updateMe(@RequestBody AppUser appUser) {
        AppUser user = AppUserUtil.getLoginAppUser();
        appUser.setId(user.getId());

        appUserService.updateAppUser(appUser);

        return appUser;
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     */
    @LogAnnotation(module = LogModule.UPDATE_PASSWORD)
    @PutMapping(value = "/users/password", params = {"oldPassword", "newPassword"})
    @ApiOperation(value = "修改密码",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword",value = "老密码"),
            @ApiImplicitParam(name = "newPassword",value = "新密码"),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public void updatePassword(String oldPassword, String newPassword) {
        if (StringUtils.isBlank(oldPassword)) {
            throw new IllegalArgumentException("旧密码不能为空");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new IllegalArgumentException("新密码不能为空");
        }

        AppUser user = AppUserUtil.getLoginAppUser();
        appUserService.updatePassword(user.getId(), oldPassword, newPassword);
    }

    /**
     * 管理后台，给用户重置密码
     *
     * @param id
     * @param newPassword
     */
    @LogAnnotation(module = LogModule.RESET_PASSWORD)
    @PreAuthorize("hasAuthority('back:user:password')")
    @PutMapping(value = "/users/{id}/password", params = {"newPassword"})
    @ApiOperation(value = "给用户重置密码",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",paramType = "path"),
            @ApiImplicitParam(name = "newPassword",value = "新密码"),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public void resetPassword(@PathVariable Long id, String newPassword) {
        appUserService.updatePassword(id, null, newPassword);
    }

    /**
     * 管理后台修改用户
     *
     * @param appUser
     */
    @LogAnnotation(module = LogModule.UPDATE_USER)
    @PreAuthorize("hasAuthority('back:user:update')")
    @PutMapping("/users")
    @ApiOperation(value = "修改用户",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appUser",value = "用户"),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public void updateAppUser(@RequestBody AppUser appUser) {
        appUserService.updateAppUser(appUser);
    }

    /**
     * 管理后台给用户分配角色
     *
     * @param id
     * @param roleIds
     */
    @LogAnnotation(module = LogModule.SET_ROLE)
    @PreAuthorize("hasAuthority('back:user:role:set')")
    @PostMapping("/users/{id}/roles")
    @ApiOperation(value = "给用户分配角色",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",paramType = "path",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "roleIds",value = "分配的用户角色id集合",dataTypeClass = Set.class),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public void setRoleToUser(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        appUserService.setRoleToUser(id, roleIds);
    }

    /**
     * 获取用户的角色
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('back:user:role:set','user:role:byuid')")
    @GetMapping("/users/{id}/roles")
    @ApiOperation(value = "获取用户角色",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",paramType = "path",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "access_token",value = "token",required = true)
    })
    public Set<SysRole> findRolesByUserId(@PathVariable Long id) {
        return appUserService.findRolesByUserId(id);
    }

    @Autowired
    private SmsClient smsClient;

    @PostMapping(value = "/users/binding-phone")
    @ApiOperation(value = "绑定手机号",httpMethod = "POST")
    public void bindingPhone(String phone, String key, String code) {
        if (StringUtils.isBlank(phone)) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key不能为空");
        }

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code不能为空");
        }

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        log.info("绑定手机号，key:{},code:{},username:{}", key, code, loginAppUser.getUsername());

        String value = smsClient.matcheCodeAndGetPhone(key, code, false, 30);
        if (value == null) {
            throw new IllegalArgumentException("验证码错误");
        }

        if (phone.equals(value)) {
            appUserService.bindingPhone(loginAppUser.getId(), phone);
        } else {
            throw new IllegalArgumentException("手机号不一致");
        }
    }
}
