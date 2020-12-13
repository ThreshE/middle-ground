package com.cloud.paymentcenter.service;


import com.cloud.common.result.ResultMap;

import javax.servlet.http.HttpServletRequest;

public interface WxPayService {
    /**
     * @Description: 微信支付统一下单
     * @param orderNo: 订单编号
     * @param amount: 实际支付金额
     * @param body: 订单描述
     * @Author:
     * @Date: 2019/8/1
     * @return
     */
    ResultMap unifiedOrder(String orderNo, double amount, String body, HttpServletRequest request) ;

    /**
     * @Description: 普通订单支付异步通知
     * @param notifyStr: 微信异步通知消息字符串
     * @Author:
     * @Date: 2019/8/1
     * @return
     */
    String notify(String notifyStr,String changeStatusUrl) throws Exception;

    /**
     * @Description: 退款
     * @param orderNo: 订单编号
     * @param amount: 实际支付金额
     * @param refundReason: 退款原因
     * @Author: XCK
     * @Date: 2019/8/6
     * @return
     */
    ResultMap refund(String orderNo, double amount, String refundReason);

}
