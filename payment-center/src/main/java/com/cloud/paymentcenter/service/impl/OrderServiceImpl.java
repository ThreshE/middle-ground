/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.paymentcenter.dao.OrderDao;
import com.cloud.paymentcenter.entity.OrderEntity;
import com.cloud.paymentcenter.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
	@Autowired
	private OrderDao orderDao;

	@Override
	public List<OrderEntity> searchUserOrderList(Map<String,Object> map){
		List<OrderEntity> list = orderDao.searchUserOrderList(map);
		return list;
	}

}
