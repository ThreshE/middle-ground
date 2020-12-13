package com.cloud.paymentcenter.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cloud.common.result.ResultMap;
import com.cloud.paymentcenter.annotion.Login;
import com.cloud.paymentcenter.entity.OrderEntity;
import com.cloud.paymentcenter.entity.UserEntity;
import com.cloud.paymentcenter.form.PayOrderForm;
import com.cloud.paymentcenter.form.WxLoginForm;
import com.cloud.paymentcenter.sdk.MyWXPayConfig;
import com.cloud.paymentcenter.sdk.WXPay;
import com.cloud.paymentcenter.sdk.WXPayUtil;
import com.cloud.paymentcenter.service.OrderService;
import com.cloud.paymentcenter.service.UserService;
import com.cloud.paymentcenter.untils.HttpContextUtils;
import com.cloud.paymentcenter.untils.JwtUtils;
import com.cloud.paymentcenter.untils.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/app/wx")
public class WxController {
    @Value("${application.wxpay.appId1}")
    private String appId1;
    @Value("${application.wxpay.appSecret1}")
    private String appSecret1;
    @Value("${application.wxpay.appId}")
    private String appId;
    @Value("${application.wxpay.key}")
    private String key;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MyWXPayConfig wxPayAppConfig;

    @PostMapping("/login")
    public ResultMap login(@RequestBody WxLoginForm form) {
        ValidatorUtils.validateEntity(form);
        //微信校验地址
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String,Object> map = new HashMap<>();
        map.put("appid",appId1);
        map.put("secret",appSecret1);
        map.put("js_code",form.getCode());
        map.put("grant_type","authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");
        if (openId == null || openId.length() == 0) {
            return ResultMap.error("临时登陆凭证错误");
        }
        UserEntity user = new UserEntity();
        user.setOpenId(openId);
        QueryWrapper wrapper = new QueryWrapper(user);
        int count = userService.count(wrapper);
        if (count == 0) {
            user.setNickname(form.getNickname());
            user.setPhoto(form.getPhoto());
            user.setType(2);
            user.setCreateTime(new Date());
            userService.save(user);
        }
        user = new UserEntity();
        user.setOpenId(openId);
        wrapper = new QueryWrapper(user);
        user = userService.getOne(wrapper);
        long id = user.getUserId();

//        //用户登录
//        long userId = userService.login(form);
//
//        //生成token
        String token = jwtUtils.generateToken(id);
//
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("expire", jwtUtils.getExpire());

        return ResultMap.ok(result);
    }

    @Login
    @PostMapping("/microAppPayOrder")
    public ResultMap microAppPayOrder(@RequestBody PayOrderForm form, @RequestHeader HashMap header,HttpServletRequest request) {
        ValidatorUtils.validateEntity(form);
        String token = header.get("token").toString();
        Long userId = Long.parseLong(jwtUtils.getClaimByToken(token).getSubject());
        int orderId = form.getOrderId();

        UserEntity user = new UserEntity();
        user.setUserId(userId);
        QueryWrapper wrapper = new QueryWrapper(user);
        long count = userService.count(wrapper);
        //判断用户是否存在
        if (count == 0) {
            return ResultMap.error("用户不存在");
        }
        //获取用户账号的openid字符串
        String openId = userService.getOne(wrapper).getOpenId();

        //判断用户是否拥有这个订单，并且订单是未支付状态
        OrderEntity order = new OrderEntity();
        order.setUserId(userId.intValue());
        order.setId(orderId);
        order.setStatus(1);
        wrapper = new QueryWrapper(order);
        count = orderService.count(wrapper);
        if (count == 0) {
            return ResultMap.error("不是有效的订单");
        }

        //查询订单详情信息
        order = new OrderEntity();
        order.setId(orderId);
        wrapper = new QueryWrapper(order);
        order = orderService.getOne(wrapper); //订单对象
        String amount = order.getAmount().multiply(new BigDecimal("100")).intValue() + ""; //订单金额

        try {
            //向微信平台发出请求，创建支付订单
            WXPay wxPay = new WXPay(wxPayAppConfig);
            Map<String,String> map = new HashMap<>();
            map.put("nonce_str", WXPayUtil.generateNonceStr()); //随机字符串
//            map.put("mch_id",wxPayAppConfig.getMchID());
//            map.put("key",wxPayAppConfig.getKey());
            map.put("body", order.getNote());
            map.put("out_trade_no", order.getCode()); //商品订单流水号
            map.put("total_fee", amount); //订单金额
            map.put("spbill_create_ip", HttpContextUtils.getIpAddr(request)); //客户端IP
            map.put("notify_url", wxPayAppConfig.getPayNotifyUrl()); //通知回调地址
            map.put("trade_type", "JSAPI");  //调用接口类型
            map.put("openid", openId); //用户授权
            log.info(map.toString());
            //创建支付订单
            Map<String, String> result = wxPay.unifiedOrder(map);
            //获取支付订单ID
            String prepayId = result.get("prepay_id");
            log.info(result.toString());
            if (prepayId != null) {
                //更新本地商品订单信息，但是不更新商品订单状态
                order.setPrepayId(prepayId); //保存支付订单ID
                order.setPaymentType(1); //支付类型
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("id", order.getId());
                orderService.update(order, updateWrapper);

                //对返回给小程序的数据生成数字签名
                map.clear();
                map.put("appId", appId);
                String timeStamp = new Date().getTime() + "";
                map.put("timeStamp", timeStamp);
                String nonceStr = WXPayUtil.generateNonceStr();
                map.put("nonceStr", nonceStr);
                map.put("package", "prepay_id=" + prepayId);
                map.put("signType", "MD5");
                //生成数字签名
                String paySign = WXPayUtil.generateSignature(map, key);
                return ResultMap.ok().put("package", "prepay_id=" + prepayId)
                        .put("timeStamp", timeStamp)
                        .put("nonceStr", nonceStr)
                        .put("paySign", paySign);
            } else {
                return ResultMap.error("支付订单创建失败");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error("微信支付模块故障");
        }
//        return ResultMap.ok();
    }

    @RequestMapping("/recieveMessage")
    public void recieveMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        Reader reader = request.getReader();
        BufferedReader buffer = new BufferedReader(reader);
        String line = buffer.readLine();
        StringBuffer temp = new StringBuffer();
        while (line != null) {
            temp.append(line);
            line = buffer.readLine();
        }
        buffer.close();
        reader.close();
        String xml=temp.toString();
        if(WXPayUtil.isSignatureValid(xml,key)){
            Map<String, String> map = WXPayUtil.xmlToMap(temp.toString());
            String resultCode = map.get("result_code");
            String returnCode = map.get("return_code");
            if ("SUCCESS".equals(resultCode) && "SUCCESS".equals(returnCode)) {
                String outTradeNo = map.get("out_trade_no");
                UpdateWrapper wrapper = new UpdateWrapper();
                wrapper.eq("code", outTradeNo);
                wrapper.set("status", 2);
                orderService.update(wrapper);
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/xml");
                Writer writer = response.getWriter();
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write("<xml><return_code><![CDATA[SUCCESS]]></return_code> <return_msg><![CDATA[OK]]></return_msg></xml>");
                bufferedWriter.close();
                writer.close();
            }
        }
        else{
            response.sendError(500,"数字签名异常");
        }

    }
}
