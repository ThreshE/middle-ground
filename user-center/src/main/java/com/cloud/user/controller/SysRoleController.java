package com.cloud.user.controller;

import java.util.Map;
import java.util.Set;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysRole;
import com.cloud.user.service.SysRoleService;

@RestController
@Api("角色控制")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 管理后台添加角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@LogAnnotation(module = LogModule.ADD_ROLE)
	@PreAuthorize("hasAuthority('back:role:save')")
	@PostMapping("/roles")
	@ApiOperation(value = "存储角色",httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysRole",value = "角色",dataTypeClass = SysRole.class),
			@ApiImplicitParam(name = "access_token",value = "token",required = true)
	})
	public SysRole save(@RequestBody SysRole sysRole) {
		if (StringUtils.isBlank(sysRole.getCode())) {
			throw new IllegalArgumentException("角色code不能为空");
		}
		if (StringUtils.isBlank(sysRole.getName())) {
			sysRole.setName(sysRole.getCode());
		}

		sysRoleService.save(sysRole);

		return sysRole;
	}

	/**
	 * 管理后台删除角色
	 * 
	 * @param id
	 */
	@LogAnnotation(module = LogModule.DELETE_ROLE)
	@PreAuthorize("hasAuthority('back:role:delete')")
	@DeleteMapping("/roles/{id}")
	@ApiOperation(value = "删除角色",httpMethod = "DELETE")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id",value = "角色id",paramType = "path",dataTypeClass = Long.class),
			@ApiImplicitParam(name = "access_token",value = "token",required = true)
	})
	public void deleteRole(@PathVariable Long id) {
		sysRoleService.deleteRole(id);
	}

	/**
	 * 管理后台修改角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@LogAnnotation(module = LogModule.UPDATE_ROLE)
	@PreAuthorize("hasAuthority('back:role:update')")
	@PutMapping("/roles")
	@ApiOperation(value = "更新角色",httpMethod = "PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysRole",value = "角色",dataTypeClass = SysRole.class),
			@ApiImplicitParam(name = "access_token",value = "token",required = true)
	})
	public SysRole update(@RequestBody SysRole sysRole) {
		if (StringUtils.isBlank(sysRole.getName())) {
			throw new IllegalArgumentException("角色名不能为空");
		}

		sysRoleService.update(sysRole);

		return sysRole;
	}

	/**
	 * 管理后台给角色分配权限
	 * 
	 * @param id
	 * @param permissionIds
	 */
	@LogAnnotation(module = LogModule.SET_PERMISSION)
	@PreAuthorize("hasAuthority('back:role:permission:set')")
	@PostMapping("/roles/{id}/permissions")
	@ApiOperation(value = "分配权限",httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id",value = "角色id",paramType = "path",dataTypeClass = Long.class),
			@ApiImplicitParam(name = "permissionIds",value = "分配的用户权限集合",dataTypeClass = Set.class),
			@ApiImplicitParam(name = "access_token",value = "token",required = true)
	})
	public void setPermissionToRole(@PathVariable Long id, @RequestBody Set<Long> permissionIds) {
		sysRoleService.setPermissionToRole(id, permissionIds);
	}

	/**
	 * 获取角色的权限
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('back:role:permission:set','role:permission:byroleid')")
	@GetMapping("/roles/{id}/permissions")
	@ApiOperation(value = "获取角色权限",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id",value = "角色id",paramType = "path",dataTypeClass = Long.class),
			@ApiImplicitParam(name = "access_token",value = "token",required = true)
	})
	public Set<SysPermission> findPermissionsByRoleId(@PathVariable Long id) {
		return sysRoleService.findPermissionsByRoleId(id);
	}

	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles/{id}")
	@ApiOperation(value = "获取角色",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id",value = "角色id",paramType = "path",dataTypeClass = Long.class),
			@ApiImplicitParam(name = "access_token",value = "token",required = true)
	})
	public SysRole findById(@PathVariable Long id) {
		return sysRoleService.findById(id);
	}

	/**
	 * 搜索角色
	 * 
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles")
	@ApiOperation(value = "搜索角色",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "params",value = "角色参数,name,角色名称",paramType = "query",dataTypeClass = Map.class),
			@ApiImplicitParam(name = "access_token",value = "token",required = true)
	})
	public Page<SysRole> findRoles(@RequestParam Map<String, Object> params) {
		return sysRoleService.findRoles(params);
	}

}
