package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限标识
 * 
 * @author 关键
 *
 */
@Data
@ApiModel("用户权限")
public class SysPermission implements Serializable {

	private static final long serialVersionUID = 280565233032255804L;
	@ApiModelProperty("权限id")
	private Long id;
	@ApiModelProperty("权限标示")
	private String permission;
	@ApiModelProperty("权限名称")
	private String name;
	private Date createTime;
	private Date updateTime;

}
