package com.cloud.business.castlers.manage;

import com.cloud.business.alibaba.ocean.rawsdk.ApiExecutor;
import com.cloud.business.alibaba.ocean.rawsdk.client.APIId;
import com.cloud.business.alibaba.ocean.rawsdk.common.SDKResult;
import com.cloud.business.alibaba.p4p.param.AlibabaCpsOpSearchCybOffersParam;
import com.cloud.business.alibaba.p4p.param.AlibabaCpsOpSearchCybOffersResult;
import com.cloud.business.alibaba.product.param.AlibabaCpsMediaProductInfoParam;
import com.cloud.business.alibaba.product.param.AlibabaCpsMediaProductInfoResult;
import com.cloud.business.castlers.constant.P1688Constant;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @description:
 * @author: 廖权名
 * @date: 2020/9/10 18:21
 */
@Service
@Slf4j
public class ProductManage {

    /**
     * 获取商品详情接口
     * com.alibaba.product:alibaba.cpsMedia.productInfo-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.product:alibaba.cpsMedia.productInfo-1
     *
     * @return SDKResult<AlibabaTradeGetBuyerViewResult>
     */
    @PostMapping("productInfo")
    @ApiOperation(value = "获取商品详情接口",notes = "获取商品详情接口")
    public SDKResult<AlibabaCpsMediaProductInfoResult> productInfo(AlibabaCpsMediaProductInfoParam param) {
        ApiExecutor apiExecutor = new ApiExecutor(P1688Constant.APP_KEY, P1688Constant.APP_SECRET);
        APIId oceanApiId = new APIId("com.alibaba.product", "alibaba.cpsMedia.productInfo", 1);
        param.setOceanApiId(oceanApiId);
        SDKResult<AlibabaCpsMediaProductInfoResult> result = apiExecutor.execute(param, P1688Constant.ACCESS_TOKE);
        log.info("{}", result.toString());
        return result;
    }


    /**
     * 商品列表搜索接口(替代alibaba.cps.listOverPricedOffer)
     * com.alibaba.p4p:alibaba.cps.op.searchCybOffers-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.p4p:alibaba.cps.op.searchCybOffers-1
     *
     * @return SDKResult<AlibabaCpsOpSearchCybOffersResult>
     */
    public SDKResult<AlibabaCpsOpSearchCybOffersResult> listOverPricedOffer(AlibabaCpsOpSearchCybOffersParam param) {
        ApiExecutor apiExecutor = new ApiExecutor(P1688Constant.APP_KEY, P1688Constant.APP_SECRET);
        APIId oceanApiId = new APIId("com.alibaba.p4p", "alibaba.cps.op.searchCybOffers", 1);
        param.setOceanApiId(oceanApiId);
        SDKResult<AlibabaCpsOpSearchCybOffersResult> result = apiExecutor.execute(param, P1688Constant.ACCESS_TOKE);
        log.info("{}", result.toString());
        return result;
    }
}
