/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.controller;

import com.cloud.common.result.ResultMap;
import com.cloud.paymentcenter.annotion.Login;
import com.cloud.paymentcenter.entity.OrderEntity;
import com.cloud.paymentcenter.form.UserOrderForm;
import com.cloud.paymentcenter.service.OrderService;
import com.cloud.paymentcenter.untils.JwtUtils;
import com.cloud.paymentcenter.untils.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author 关键
 */
@Slf4j
@RestController
@RequestMapping("/app/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtUtils jwtUtils;

    @Login
    @PostMapping("/searchUserOrderList")
    public ResultMap searchUserOrderList(@RequestBody UserOrderForm form, @RequestHeader HashMap header){
        ValidatorUtils.validateEntity(form);
        String token= header.get("token").toString();
        int userId = Integer.parseInt(jwtUtils.getClaimByToken(token).getSubject());
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("start",start);
        map.put("length",length);
        List<OrderEntity> list = orderService.searchUserOrderList(map);
        return ResultMap.ok().put("list",list);
    }
}
