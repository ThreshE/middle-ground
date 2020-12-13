//package com.cloud.paymentcenter.controller;
//
//import com.cloud.common.result.ResultMap;
//import com.cloud.paymentcenter.service.WxPayService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//@RestController
//@RequestMapping("/wxPay")
//@Slf4j
//public class WxPayController {
//
//    @Autowired
//    private WxPayService wxPayService;
//    private RestTemplate restTemplate = new RestTemplate();
//
//    /**
//     * 普通订单下单接口
//     */
//    @GetMapping("/unifiedOrder")
//    public ResultMap unifiedOrder(
//            @RequestParam String orderNo,
//            @RequestParam double amount,
//            @RequestParam String body,
//            HttpServletRequest request,
//            @RequestParam String orderExitUrl) {
//        try {
//            // 1、验证订单是否存在
//            if (restTemplate.getForObject(orderExitUrl + orderNo, Boolean.class)) {
//                // 2、开始微信支付统一下单
//                ResultMap resultMap = wxPayService.unifiedOrder(orderNo, amount, body, request);
//                return resultMap;
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return ResultMap.error("运行异常，请联系管理员");
//        }
//        return ResultMap.error("订单不存在");
//    }
//
//    /**
//     * 微信支付异步通知
//     */
//    @RequestMapping(value = "/notify")
//    public String payNotify(HttpServletRequest request, @RequestParam String type) {
//        InputStream is = null;
//        String xmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
//        try {
//            is = request.getInputStream();
//            // 将InputStream转换成String
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            xmlBack = wxPayService.notify(sb.toString(),type);
//        } catch (Exception e) {
//            log.error("微信手机支付回调通知失败：", e);
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return xmlBack;
//    }
//
//    @PostMapping("/refund")
//    public ResultMap refund(@RequestParam String orderNo,
//                            @RequestParam double amount,
//                            @RequestParam(required = false) String refundReason){
//
//        return wxPayService.refund(orderNo, amount, refundReason);
//    }
//
//}
