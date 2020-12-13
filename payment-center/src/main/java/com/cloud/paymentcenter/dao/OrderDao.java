/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.paymentcenter.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
    @Select("SELECT o.id,o.code,o.user_id,o.amount,o.note,o.payment_type,o.prepay_id,o.status,o.create_time," +
            "o.amount FROM tb_order o WHERE o.user_id=#{userId} LIMIT #{start},#{length}")
    List<OrderEntity> searchUserOrderList(Map<String,Object> map);
}
