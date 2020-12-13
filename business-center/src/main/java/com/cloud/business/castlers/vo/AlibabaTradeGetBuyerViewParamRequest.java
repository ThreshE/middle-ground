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
public class AlibabaTradeGetBuyerViewParamRequest {

    @ApiModelProperty(value = "站点信息，指定调用的API是属于国际站（alibaba）还是1688网站（1688）",required = true, allowableValues = "1688,alibaba")
    private String webSite;


    @ApiModelProperty(value = "交易的订单id",required = true)
    private Long orderId;


    @ApiModelProperty("     * 设置查询结果中包含的域，GuaranteesTerms：保障条款，NativeLogistics：物流信息，RateDetail：评价详情，OrderInvoice：发票信息。默认返回GuaranteesTerms、NativeLogistics、OrderInvoice。     *\n" +
            "     * 参数示例：<pre>GuaranteesTerms,NativeLogistics,RateDetail,OrderInvoice</pre>\n" )
    private String includeFields;

    @ApiModelProperty("     * 设置垂直表中的attributeKeys     *\n" +
            "     * 参数示例：<pre>[]</pre>")
    private String[] attributeKeys;

}
