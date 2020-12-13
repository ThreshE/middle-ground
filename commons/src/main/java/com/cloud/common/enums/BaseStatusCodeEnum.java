package com.cloud.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @description: 响应码枚举
 * @author: 廖权名
 * @date: 2020/7/11
 */
public enum BaseStatusCodeEnum {
    /**
     * 成功200
     */
    CODE_200(200),

    CODE_201(201),
    /**
     * （未授权）请求要求身份验证,或者权限不足。
     */
    CODE_401(401),

    /**
     * 失败 500
     */
    CODE_500(500);



    /**
     * 状态码
     */
    Integer code;

    BaseStatusCodeEnum(Integer code) {
        this.code=code;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(code);
    }
}
