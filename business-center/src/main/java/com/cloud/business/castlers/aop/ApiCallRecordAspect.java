package com.cloud.business.castlers.aop;

import com.alibaba.fastjson.JSONObject;
import com.cloud.business.castlers.entity.ApiCallRecords;
import com.cloud.business.castlers.mapper.ApiCallRecordMapper;
import com.cloud.business.castlers.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 *
 * @description:
 * @author: 廖权名
 * @date: 2020/9/10 18:21
 */
@Aspect
@Component
@Slf4j
public class ApiCallRecordAspect {

    private static ThreadLocal<ApiCallRecords> localApiCallRecords = new ThreadLocal<>();

    @Resource
    ApiCallRecordMapper apiCallRecordMapper;


    @Pointcut("execution(* com.cloud.business.castlers.controller..*Controller.*(..))")
    public void apiCall() {

    }

    @Before(value = "apiCall()")
    public void before(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException {
        //获取连接点的方法签名对象
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Object target = joinPoint.getTarget();
        //获取到当前执行的方法
        Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        Object[] objects = joinPoint.getArgs();
        String requestParamte = JSONObject.toJSONString(objects);

        ApiCallRecords records = new ApiCallRecords();
        records.setRequestParameters(requestParamte.length() >= 9999 ? requestParamte.substring(0, 9999) : requestParamte);
        records.setIp(RequestUtils.getIpAddress());
        Enumeration<String> headers = RequestUtils.getRequest().getHeaders("content-type");
        if (headers.hasMoreElements()) {
            records.setContentType(RequestUtils.getRequest().getHeaders("content-type").nextElement());
        }

        localApiCallRecords.set(records);
    }

    @AfterReturning(value = "apiCall()", returning = "res")
    public void afterReturning(JoinPoint joinPoint, Object res) {
        ApiCallRecords records = localApiCallRecords.get();
        String rep = JSONObject.toJSONString(res);
        records.setResponseParameters(rep.length() >= 9999 ? rep.substring(0, 9999) : rep);
        records.setSuccessFlag(1);
        apiCallRecordMapper.insert(records);
        localApiCallRecords.remove();
    }

    @AfterThrowing(value = "apiCall()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        ApiCallRecords records = localApiCallRecords.get();
        String rep = ex.getMessage();
        records.setResponseParameters(rep.length() >= 9999 ? rep.substring(0, 9999) : rep);
        records.setSuccessFlag(0);
        apiCallRecordMapper.insert(records);
        localApiCallRecords.remove();
    }


}
