package com.cloud.business.castlers.exception;

import com.cloud.common.bean.CommonResult;
import com.cloud.common.enums.BaseStatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:全局异常捕获
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonResult handleCommonExp(HttpServletRequest request, Exception ex){
        log.error("Exception type " + ex.getClass().getTypeName());
        log.error("Exception exception " + ex.getMessage());
        CommonResult<Object> result = new CommonResult<>();
        result.setMessage(ex.getMessage());
        result.setSuccess(false);
        result.setCode(BaseStatusCodeEnum.CODE_500);
        return result;
    }
}