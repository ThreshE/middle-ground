package com.cloud.business.castlers.manage;

import com.cloud.business.alibaba.logistics.param.AlibabaTradeGetLogisticsInfosBuyerViewParam;
import com.cloud.business.alibaba.logistics.param.AlibabaTradeGetLogisticsInfosBuyerViewResult;
import com.cloud.business.alibaba.ocean.rawsdk.ApiExecutor;
import com.cloud.business.alibaba.ocean.rawsdk.client.APIId;
import com.cloud.business.alibaba.ocean.rawsdk.common.SDKResult;
import com.cloud.business.castlers.constant.P1688Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @description:
 * @author: 廖权名
 * @date: 2020/9/10 18:21
 */
@Service
@Slf4j
public class LogisticsManage {

    /**
     * 获取交易订单的物流信息(买家视角)
     * com.alibaba.logistics:alibaba.trade.getLogisticsInfos.buyerView-1
     * link to https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.logistics:alibaba.trade.getLogisticsInfos.buyerView-1&aopApiCategory=Logistics_NEW
     *
     * @return SDKResult<AlibabaTradeGetLogisticsInfosBuyerViewResult>
     */
    public SDKResult<AlibabaTradeGetLogisticsInfosBuyerViewResult> getLogisticsInfosBuyerView(AlibabaTradeGetLogisticsInfosBuyerViewParam param) {
        ApiExecutor apiExecutor = new ApiExecutor(P1688Constant.APP_KEY, P1688Constant.APP_SECRET);
        APIId oceanApiId = new APIId("com.alibaba.logistics", "alibaba.trade.getLogisticsInfos.buyerView", 1);
        param.setOceanApiId(oceanApiId);
        SDKResult<AlibabaTradeGetLogisticsInfosBuyerViewResult> result = apiExecutor.execute(param, P1688Constant.ACCESS_TOKE);
        log.info("{}", result.toString());
        return result;
    }
}
