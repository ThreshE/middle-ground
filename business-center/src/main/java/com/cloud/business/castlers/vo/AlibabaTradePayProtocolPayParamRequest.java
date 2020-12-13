package com.cloud.business.castlers.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlibabaTradePayProtocolPayParamRequest {

    @ApiModelProperty("设置订单ID")
    private Long orderId;

}
