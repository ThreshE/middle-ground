/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.paymentcenter.entity.UserEntity;
import com.cloud.paymentcenter.form.LoginForm;


public interface UserService extends IService<UserEntity> {


	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);
}
