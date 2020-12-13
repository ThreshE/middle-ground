package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户")
@TableName("app_user")
public class AppUser implements Serializable {

	private static final long serialVersionUID = 611197991672067628L;
	@ApiModelProperty("用户id")
	@TableId()
	private Long id;
	@ApiModelProperty("用户名称")
	private String username;
	@ApiModelProperty("密码")
	private String password;
	@ApiModelProperty("昵称")
	private String nickname;
	@ApiModelProperty("头像url")
	private String headImgUrl;
	@ApiModelProperty("电话号码")
	private String phone;
	@ApiModelProperty("性别")
	private Integer sex;
	@ApiModelProperty("是否可用")
	private Boolean enabled;
	@ApiModelProperty("用户类型")
	private String type;
	private Date createTime;
	private Date updateTime;

}
