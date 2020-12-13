package com.cloud.business.castlers.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.business.alibaba.logistics.param.AlibabaTradeGetLogisticsInfosBuyerViewParam;
import com.cloud.business.alibaba.logistics.param.AlibabaTradeGetLogisticsInfosBuyerViewResult;
import com.cloud.business.alibaba.ocean.rawsdk.common.SDKResult;
import com.cloud.business.castlers.manage.LogisticsManage;
import com.cloud.business.castlers.vo.AlibabaTradeGetLogisticsInfosBuyerViewParamRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @description: 物流接口
 * @author: 廖权名
 * @date: 2020/9/9 22:01
 */
@Slf4j
@RequestMapping("logistics")
@RestController
@Api(value = "物流接口",tags = "物流接口")
public class LogisticsController {

    @Resource
    LogisticsManage logisticsManage;

    /**
     * 获取交易订单的物流信息(买家视角)
     * com.alibaba.logistics:alibaba.trade.getLogisticsInfos.buyerView-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.logistics:alibaba.trade.getLogisticsInfos.buyerView-1&aopApiCategory=Logistics_NEW
     *
     * @return SDKResult<AlibabaTradeGetLogisticsInfosBuyerViewResult>
     */
    @PostMapping("getLogisticsInfosBuyerView")
    @ApiOperation(value = "获取交易订单的物流信息(买家视角)",notes = "获取交易订单的物流信息(买家视角)")
    public SDKResult<AlibabaTradeGetLogisticsInfosBuyerViewResult> getLogisticsInfosBuyerView(AlibabaTradeGetLogisticsInfosBuyerViewParamRequest param) {

        SDKResult<AlibabaTradeGetLogisticsInfosBuyerViewResult> result = logisticsManage.getLogisticsInfosBuyerView(BeanUtil.copyProperties(param,AlibabaTradeGetLogisticsInfosBuyerViewParam.class));

        return result;
    }
}
