package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色")
public class SysRole implements Serializable {

	private static final long serialVersionUID = -2054359538140713354L;
	@ApiModelProperty("角色id")
	private Long id;
	@ApiModelProperty("角色编码")
	private String code;
	@ApiModelProperty("角色名称")
	private String name;
	private Date createTime;
	private Date updateTime;
}
