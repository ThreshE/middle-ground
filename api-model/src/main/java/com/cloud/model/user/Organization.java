package com.cloud.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("组织")
public class Organization implements Serializable{
    @ApiModelProperty("组织id")
    private Integer id;
    @ApiModelProperty("组织名称")
    private String name;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("电话")
    private String tel;
    @ApiModelProperty("负责人")
    private String charger;
}
