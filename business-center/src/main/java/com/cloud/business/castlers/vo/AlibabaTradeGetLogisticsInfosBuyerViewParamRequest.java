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
public class AlibabaTradeGetLogisticsInfosBuyerViewParamRequest {

    @ApiModelProperty(value = "设置订单号",required = true)
    private Long orderId;


    @ApiModelProperty(value = "     * 设置需要返回的字段，目前有:company.name,sender,receiver,sendgood。返回的字段要用英文逗号分隔开     *\n" +
            "     * 参数示例：<pre>company,name,sender,receiver,sendgood</pre>     ")
    private String fields;


    @ApiModelProperty(value = "站点信息，指定调用的API是属于国际站（alibaba）还是1688网站（1688）",required = true, allowableValues = "1688,alibaba")
    private String webSite;

}
