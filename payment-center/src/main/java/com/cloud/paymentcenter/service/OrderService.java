/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.paymentcenter.entity.OrderEntity;

import java.util.List;
import java.util.Map;


public interface OrderService extends IService<OrderEntity> {

	List<OrderEntity> searchUserOrderList(Map<String,Object> map);
}
