/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.form;

import lombok.Data;

import javax.validation.constraints.Min;


@Data
public class PayOrderForm {
    @Min(1)
    private Integer orderId;
}
