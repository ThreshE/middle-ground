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
public class AlibabaTradeGetBuyerOrderListParamRequest {


    @ApiModelProperty("业务类型，支持： \"cn\"(普通订单类型), \"ws\"(大额批发订单类型), \"yp\"(普通拿样订单类型), \"yf\"(一分钱拿样订单类型), \"fs\"(倒批(限时折扣)订单类型), \"cz\"(加工定制订单类型), \"ag\"(协议采购订单类型), \"hp\"(伙拼订单类型), \"gc\"(国采订单类型), \"supply\"(供销订单类型), \"nyg\"(nyg订单类型), \"factory\"(淘工厂订单类型), \"quick\"(快订下单), \"xiangpin\"(享拼订单), \"nest\"(采购商城-鸟巢), \"f2f\"(当面付), \"cyfw\"(存样服务), \"sp\"(代销订单标记), \"wg\"(微供订单), \"factorysamp\"(淘工厂打样订单), \"factorybig\"(淘工厂大货订单)")
    private String[] bizTypes;


    //yyyyMMddHHmmssSSSZ
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("下单结束时间 yyyy-MM-dd HH:mm:ss")
    private Date createEndTime;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("下单开始时间 yyyy-MM-dd HH:mm:ss")
    private Date createStartTime;


    @ApiModelProperty("设置是否查询历史订单表,默认查询当前表，即默认值为false\n" +
            "参数示例：false")
    private Boolean isHis;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("查询修改时间结束 yyyy-MM-dd HH:mm:ss")
    private Date modifyEndTime;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("查询修改时间开始yyyy-MM-dd HH:mm:ss")
    private Date modifyStartTime;

    @ApiModelProperty("设置订单状态，值有 success, cancel(交易取消，违约金等交割完毕), waitbuyerpay(等待卖家付款)， waitsellersend(等待卖家发货), waitbuyerreceive(等待买家收货 )")
    private String orderStatus;


    @ApiModelProperty(value = "查询分页页码，从1开始")
    private Integer page;


    @ApiModelProperty("查询的每页的数量")
    private Integer pageSize;

    @ApiModelProperty("退款状态，支持： \"waitselleragree\"(等待卖家同意), \"refundsuccess\"(退款成功), \"refundclose\"(退款关闭), \"waitbuyermodify\"(待买家修改), \"waitbuyersend\"(等待买家退货), \"waitsellerreceive\"(等待卖家确认收货)")
    private String refundStatus;


    @ApiModelProperty("卖家memberId")
    private String sellerMemberId;


    @ApiModelProperty("卖家loginId")
    private String sellerLoginId;

    @ApiModelProperty("卖家评价状态 (4:已评价,5:未评价,6;不需要评价)")
    private Integer sellerRateStatus;

    @ApiModelProperty("交易类型:\n" +
            "担保交易(1),\n" +
            "预存款交易(2),\n" +
            "ETC境外收单交易(3),\n" +
            "即时到帐交易(4),\n" +
            "保障金安全交易(5),\n" +
            "统一交易流程(6),\n" +
            "分阶段交易(7),\n" +
            "货到付款交易(8),\n" +
            "信用凭证支付交易(9),\n" +
            "账期支付交易(10),\n" +
            "1688交易4.0，新分阶段交易(50060),\n" +
            "当面付的交易流程(50070),\n" +
            "服务类的交易流程(50080)")
    private String tradeType;


    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("是否需要查询买家的详细地址信息和电话")
    private Boolean needBuyerAddressAndPhone;


    @ApiModelProperty("是否需要查询备注信息")
    private Boolean needMemoInfo;


}
