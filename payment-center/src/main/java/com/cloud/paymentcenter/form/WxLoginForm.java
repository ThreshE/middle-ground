/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cloud.paymentcenter.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class WxLoginForm {
    @NotBlank(message="临时登陆凭证不能为空")
    private String code;

    @NotBlank(message="昵称不能为空")
    private String nickname;

    @NotBlank(message="头像URL不能为空")
    private String photo;

}
