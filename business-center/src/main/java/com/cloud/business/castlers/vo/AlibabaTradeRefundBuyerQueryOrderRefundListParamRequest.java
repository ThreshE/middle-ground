package com.cloud.business.castlers.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlibabaTradeRefundBuyerQueryOrderRefundListParamRequest  {

    @ApiModelProperty(value = "设置订单ID",required = true)
    private Long orderId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("退款申请时间（起始） yyyy-MM-dd HH:mm:ss")
    private Date applyStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("退款申请时间（截止）） yyyy-MM-dd HH:mm:ss")
    private Date applyEndTime;



    @ApiModelProperty("设置退款状态列表   " +
            "参数示例：<pre>等待卖家同意 waitselleragree;退款成功 refundsuccess;退款关闭 refundclose;待买家修改 waitbuyermodify;等待买家退货 waitbuyersend;等待卖家确认收货 waitsellerreceive</pre>")
    private String[] refundStatusSet;


    @ApiModelProperty("设置卖家memberId 参数示例：<pre>b2b-1623492085</pre> ")
    private String sellerMemberId;


    @ApiModelProperty("当前页码 参数示例：<pre>0</pre>  ")
    private Integer currentPageNum;


    @ApiModelProperty("设置每页条数 参数示例：<pre>20</pre> ")
    private Integer pageSize;


    @ApiModelProperty("设置退货物流单号（传此字段查询时，需同时传入sellerMemberId）参数示例：<pre>3101***159271</pre> ")
    private String logisticsNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("退款修改时间(起始) yyyy-MM-dd HH:mm:ss")
    private Date modifyStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("退款修改时间(截止) yyyy-MM-dd HH:mm:ss")
    private Date modifyEndTime;

    @ApiModelProperty("设置1:售中退款，2:售后退款；0:所有退款单  参数示例：<pre>1</pre>   ")
    private Integer dipsuteType;


}
