package com.cloud.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("子系统")
public class ChildSystem implements Serializable {
    @ApiModelProperty("子系统id")
    private Integer id;
    @ApiModelProperty("子系统名称")
    private String name;
}
