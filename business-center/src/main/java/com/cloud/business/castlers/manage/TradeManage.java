package com.cloud.business.castlers.manage;

import com.cloud.business.alibaba.ocean.rawsdk.ApiExecutor;
import com.cloud.business.alibaba.ocean.rawsdk.client.APIId;
import com.cloud.business.alibaba.ocean.rawsdk.common.SDKResult;
import com.cloud.business.alibaba.trade.param.*;
import com.cloud.business.castlers.constant.P1688Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @description:
 * @author: 廖权名
 * @date: 2020/9/10 18:21
 */
@Service
@Slf4j
public class TradeManage {

    /**
     * 订单列表查看(买家视角)
     * com.alibaba.trade:alibaba.trade.getBuyerOrderList-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.trade:alibaba.trade.getBuyerOrderList-1&aopApiCategory=trade_new
     *
     * @return SDKResult<AlibabaTradeGetBuyerOrderListResult>
     */
    public SDKResult<AlibabaTradeGetBuyerOrderListResult> getBuyerOrderList(AlibabaTradeGetBuyerOrderListParam param) {
        ApiExecutor apiExecutor = new ApiExecutor(P1688Constant.APP_KEY, P1688Constant.APP_SECRET);
        APIId oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.getBuyerOrderList", 1);
        //查询接口条件
        param.setOceanApiId(oceanApiId);
        SDKResult<AlibabaTradeGetBuyerOrderListResult> result = apiExecutor.execute(param, P1688Constant.ACCESS_TOKE);
        log.info("{}", result.toString());
        return result;
    }

    /**
     * 订单详情查看(买家视角)
     * com.alibaba.trade:alibaba.trade.get.buyerView-1
     * link to https://open.1688.com/api/apidocdetail.htm?spm=0.$!pageSpm.0.0.750b55edIPmYXr&id=com.alibaba.trade:alibaba.trade.get.buyerView-1&aopApiCategory=trade_new
     *
     * @return SDKResult<AlibabaTradeGetBuyerViewResult>
     */
    public SDKResult<AlibabaTradeGetBuyerViewResult> getBuyerView(AlibabaTradeGetBuyerViewParam param) {
        ApiExecutor apiExecutor = new ApiExecutor(P1688Constant.APP_KEY, P1688Constant.APP_SECRET);
        APIId oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.get.buyerView", 1);
        param.setOceanApiId(oceanApiId);
        SDKResult<AlibabaTradeGetBuyerViewResult> result = apiExecutor.execute(param, P1688Constant.ACCESS_TOKE);
        log.info("{}", result.toString());
        return result;
    }

    /**
     * 查询退款单列表(买家视角)
     * com.alibaba.trade:alibaba.trade.refund.buyer.queryOrderRefundList-1
     * link to https://open.1688.com/api/apidocdetail.htm?aopApiCategory=trade_new&id=com.alibaba.trade:alibaba.trade.refund.buyer.queryOrderRefundList-1
     *
     * @return SDKResult<AlibabaTradeRefundBuyerQueryOrderRefundListResult>
     */
    public SDKResult<AlibabaTradeRefundBuyerQueryOrderRefundListResult> queryOrderRefundList(AlibabaTradeRefundBuyerQueryOrderRefundListParam param) {
        ApiExecutor apiExecutor = new ApiExecutor(P1688Constant.APP_KEY, P1688Constant.APP_SECRET);
        APIId oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.refund.buyer.queryOrderRefundList", 1);
        param.setOceanApiId(oceanApiId);
        SDKResult<AlibabaTradeRefundBuyerQueryOrderRefundListResult> result = apiExecutor.execute(param, P1688Constant.ACCESS_TOKE);
        log.info("{}", result.toString());
        return result;
    }

    /**
     * 支付宝协议代扣支付
     * com.alibaba.trade:alibaba.trade.pay.protocolPay-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.trade:alibaba.trade.pay.protocolPay-1
     *
     * @return SDKResult<AlibabaTradePayProtocolPayResult>
     */
    public SDKResult<AlibabaTradePayProtocolPayResult> payProtocolPay(AlibabaTradePayProtocolPayParam param) {
        ApiExecutor apiExecutor = new ApiExecutor(P1688Constant.APP_KEY, P1688Constant.APP_SECRET);
        APIId oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.pay.protocolPay", 1);
        param.setOceanApiId(oceanApiId);
        SDKResult<AlibabaTradePayProtocolPayResult> result = apiExecutor.execute(param, P1688Constant.ACCESS_TOKE);
        log.info("{}", result.toString());
        return result;
    }

}
