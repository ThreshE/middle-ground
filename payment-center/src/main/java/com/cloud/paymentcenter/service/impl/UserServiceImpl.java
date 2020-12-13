/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.paymentcenter.dao.UserDao;
import com.cloud.paymentcenter.entity.UserEntity;
import com.cloud.paymentcenter.exception.PaymentException;
import com.cloud.paymentcenter.form.LoginForm;
import com.cloud.paymentcenter.service.UserService;
import com.cloud.paymentcenter.untils.Assert;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Override
	public long login(LoginForm form) {
		QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
		wrapper.eq("username",form.getUsername());
		UserEntity user = baseMapper.selectOne(wrapper);
		Assert.isNull(user, "用户名错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new PaymentException("密码错误");
		}

		return user.getUserId();
	}
}
