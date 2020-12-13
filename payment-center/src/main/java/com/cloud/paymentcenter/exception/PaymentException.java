/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.exception;

import lombok.Data;

/**
 * 自定义异常
 *
 * @author 关键
 */
@Data
public class PaymentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public PaymentException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public PaymentException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public PaymentException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public PaymentException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}
}
