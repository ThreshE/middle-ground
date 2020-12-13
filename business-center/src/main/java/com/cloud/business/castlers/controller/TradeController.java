package com.cloud.business.castlers.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.business.alibaba.ocean.rawsdk.common.SDKResult;
import com.cloud.business.alibaba.trade.param.*;
import com.cloud.business.castlers.manage.TradeManage;
import com.cloud.business.castlers.vo.AlibabaTradeGetBuyerOrderListParamRequest;
import com.cloud.business.castlers.vo.AlibabaTradeGetBuyerViewParamRequest;
import com.cloud.business.castlers.vo.AlibabaTradePayProtocolPayParamRequest;
import com.cloud.business.castlers.vo.AlibabaTradeRefundBuyerQueryOrderRefundListParamRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @description: 訂單接口
 * @author: 廖权名
 * @date: 2020/9/9 22:02
 */
@Slf4j
@RestController
@RequestMapping("trade")
@Api(value = "訂單接口",tags = "訂單接口")
public class TradeController {

    @Resource
    TradeManage tradeManage;

    /**
     * 订单详情查看(买家视角)
     * com.alibaba.trade:alibaba.trade.get.buyerView-1
     * link to https://open.1688.com/api/apidocdetail.htm?spm=0.$!pageSpm.0.0.750b55edIPmYXr&id=com.alibaba.trade:alibaba.trade.get.buyerView-1&aopApiCategory=trade_new
     *
     * @return SDKResult<AlibabaTradeGetBuyerViewResult>
     */
    @PostMapping("getBuyerView")
    @ApiOperation(value = "订单详情查看(买家视角)",notes = "订单详情查看(买家视角)")
    public SDKResult<AlibabaTradeGetBuyerViewResult> getBuyerView(AlibabaTradeGetBuyerViewParamRequest param) {
        SDKResult<AlibabaTradeGetBuyerViewResult> result = tradeManage.getBuyerView(BeanUtil.copyProperties(param,AlibabaTradeGetBuyerViewParam.class));
        return result;
    }

    /**
     * 订单列表查看(买家视角)
     * com.alibaba.trade:alibaba.trade.getBuyerOrderList-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.trade:alibaba.trade.getBuyerOrderList-1&aopApiCategory=trade_new
     *
     * @return SDKResult<AlibabaTradeGetBuyerOrderListResult>
     */
    @ApiOperation(value = "订单列表查看(买家视角)",notes = "订单列表查看(买家视角)")
    @PostMapping("getBuyerOrderList")
    public SDKResult<AlibabaTradeGetBuyerOrderListResult> getBuyerOrderList(AlibabaTradeGetBuyerOrderListParamRequest param) {

        SDKResult<AlibabaTradeGetBuyerOrderListResult> result = tradeManage.getBuyerOrderList(BeanUtil.copyProperties(param,AlibabaTradeGetBuyerOrderListParam.class));
        return result;
    }

    /**
     * 查询退款单列表(买家视角)
     * com.alibaba.trade:alibaba.trade.refund.buyer.queryOrderRefundList-1
     * link to https://open.1688.com/api/apidocdetail.htm?aopApiCategory=trade_new&id=com.alibaba.trade:alibaba.trade.refund.buyer.queryOrderRefundList-1
     *
     * @return SDKResult<AlibabaTradeRefundBuyerQueryOrderRefundListResult>
     */
    @ApiOperation(value = "查询退款单列表(买家视角)",notes = "查询退款单列表(买家视角)")
    @PostMapping("queryOrderRefundList")
    public SDKResult<AlibabaTradeRefundBuyerQueryOrderRefundListResult> queryOrderRefundList(AlibabaTradeRefundBuyerQueryOrderRefundListParamRequest param) {
        SDKResult<AlibabaTradeRefundBuyerQueryOrderRefundListResult> result = tradeManage.queryOrderRefundList(BeanUtil.copyProperties(param, AlibabaTradeRefundBuyerQueryOrderRefundListParam.class));
        return result;
    }

    /**
     * 支付宝协议代扣支付
     * com.alibaba.trade:alibaba.trade.pay.protocolPay-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.trade:alibaba.trade.pay.protocolPay-1
     *
     * @return SDKResult<AlibabaTradePayProtocolPayResult>
     */
    @PostMapping("payProtocolPay")
    @ApiOperation(value = "支付宝协议代扣支付",notes = "支付宝协议代扣支付")
    public SDKResult<AlibabaTradePayProtocolPayResult> payProtocolPay(AlibabaTradePayProtocolPayParamRequest param) {
        SDKResult<AlibabaTradePayProtocolPayResult> result = tradeManage.payProtocolPay(BeanUtil.copyProperties(param, AlibabaTradePayProtocolPayParam.class));
        return result;
    }
}
