package com.cloud.common.bean;

import com.cloud.common.enums.BaseStatusCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="统一接口返回对象", description="统一接口返回对象")
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private BaseStatusCodeEnum code=BaseStatusCodeEnum.CODE_200;

    /**
     * 返回数据对象 data
     */
    @ApiModelProperty(value = "返回数据对象")
    private T data;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    public CommonResult() {

    }

    public CommonResult<T> success(String message) {
        this.message = message;
        this.code = BaseStatusCodeEnum.CODE_200 ;
        this.success = true;
        return this;
    }

    public CommonResult<T> success(T data) {
        this.data = data;
        this.code = BaseStatusCodeEnum.CODE_200 ;
        this.success = true;
        return this;
    }


    public static CommonResult<Object> ok() {
        CommonResult<Object> r = new CommonResult<Object>();
        r.setSuccess(true);
        r.setCode(BaseStatusCodeEnum.CODE_200);
        r.setMessage("成功");
        return r;
    }

    public static CommonResult<Object> ok(String msg) {
        CommonResult<Object> r = new CommonResult<Object>();
        r.setSuccess(true);
        r.setCode(BaseStatusCodeEnum.CODE_200);
        r.setMessage(msg);
        return r;
    }

    public static CommonResult<Object> ok(Object data) {
        CommonResult<Object> r = new CommonResult<Object>();
        r.setSuccess(true);
        r.setCode(BaseStatusCodeEnum.CODE_200);
        r.setData(data);
        return r;
    }

    public static CommonResult<Object> error(String msg) {
        return error(BaseStatusCodeEnum.CODE_500, msg);
    }

    public static CommonResult<Object> error(BaseStatusCodeEnum code, String msg) {
        CommonResult<Object> r = new CommonResult<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public CommonResult<T> error500(String message) {
        this.message = message;
        this.code = BaseStatusCodeEnum.CODE_500;
        this.success = false;
        return this;
    }
    /**
     * 无权限访问返回结果
     */
    public static CommonResult<Object> noauth(String msg) {
        return error(BaseStatusCodeEnum.CODE_401, msg);
    }
}