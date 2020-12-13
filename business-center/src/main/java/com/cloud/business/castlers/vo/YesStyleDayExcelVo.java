package com.cloud.business.castlers.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ToString
@NoArgsConstructor
@Builder
@ApiModel
@AllArgsConstructor
public class YesStyleDayExcelVo {

    @ApiModelProperty("编号")
    @Excel(name = "编号",width = 20)
    private String dayCode;

    @ApiModelProperty("付款时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "付款时间",width = 20)
    private String payTime;

    @ApiModelProperty("1688订单号")
    @Excel(name = "1688订单号",width = 20)
    private String alibabaTradeCode;

    @ApiModelProperty("产品名称")
    @Excel(name = "产品名称",width = 20)
    private String productName;

    @ApiModelProperty("数量")
    @Excel(name = "数量",width = 20)
    private Integer count;

    @ApiModelProperty("单价")
    @Excel(name = "单价",width = 20)
    private String priceSingle;

    @ApiModelProperty("运费")
    @Excel(name = "运费",width = 20)
    private String carriage;

    @ApiModelProperty("总额")
    @Excel(name = "总额",width = 20)
    private String priceTotal;

    @ApiModelProperty("下单员")
    @Excel(name = "下单员",width = 20)
    private String buyer;

}
