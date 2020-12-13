package com.cloud.business.castlers.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.business.alibaba.ocean.rawsdk.common.SDKResult;
import com.cloud.business.alibaba.p4p.param.AlibabaCpsOpSearchCybOffersParam;
import com.cloud.business.alibaba.p4p.param.AlibabaCpsOpSearchCybOffersResult;
import com.cloud.business.alibaba.product.param.AlibabaCpsMediaProductInfoParam;
import com.cloud.business.alibaba.product.param.AlibabaCpsMediaProductInfoResult;
import com.cloud.business.castlers.manage.ProductManage;
import com.cloud.business.castlers.vo.AlibabaCpsMediaProductInfoParamRequest;
import com.cloud.business.castlers.vo.AlibabaCpsOpSearchCybOffersParamRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @description: 商品接口
 * @author: 廖权名
 * @date: 2020/9/10 17:29
 */
@Slf4j
@RestController
@RequestMapping("product")
@Api(value = "商品接口",tags = "商品接口")
public class ProductController {


    @Resource
    ProductManage productManage;

    /**
     * 获取商品详情接口
     * com.alibaba.product:alibaba.cpsMedia.productInfo-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.product:alibaba.cpsMedia.productInfo-1
     *
     * @return SDKResult<AlibabaCpsMediaProductInfoResult>
     */
    @PostMapping("productInfo")
    @ApiOperation(value = "获取商品详情接口",notes = "获取商品详情接口")
    public SDKResult<AlibabaCpsMediaProductInfoResult> productInfo(AlibabaCpsMediaProductInfoParamRequest param) {
        SDKResult<AlibabaCpsMediaProductInfoResult> result = productManage.productInfo(BeanUtil.copyProperties(param, AlibabaCpsMediaProductInfoParam.class));
        return result;
    }

    /**
     * 商品列表搜索接口(替代alibaba.cps.listOverPricedOffer)
     * com.alibaba.p4p:alibaba.cps.op.searchCybOffers-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.p4p:alibaba.cps.op.searchCybOffers-1
     *
     * @return SDKResult<AlibabaCpsOpSearchCybOffersResult>
     */
    @PostMapping("listOverPricedOffer")
    @ApiOperation(value = "商品列表搜索接口",notes = "商品列表搜索接口")
    public SDKResult<AlibabaCpsOpSearchCybOffersResult> listOverPricedOffer(AlibabaCpsOpSearchCybOffersParamRequest param) {
        SDKResult<AlibabaCpsOpSearchCybOffersResult> result = productManage.listOverPricedOffer(BeanUtil.copyProperties(param, AlibabaCpsOpSearchCybOffersParam.class));
        return result;
    }

}
