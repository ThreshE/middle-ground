package com.cloud.business.alibaba.p4p.param;

/**
 *
 * @description:
 * @author: 廖权名
 * @date: 2020/9/10 18:21
 */
public class AlibabaCpsOpSearchCybOffersResult {

    private AlibabaAscXuanwuCoreDtoPageResultDTO result;

    /**
     * @return 结果
     */
    public AlibabaAscXuanwuCoreDtoPageResultDTO getResult() {
        return result;
    }

    /**
     * 设置结果     *
          
     * 此参数必填
     */
    public void setResult(AlibabaAscXuanwuCoreDtoPageResultDTO result) {
        this.result = result;
    }

}
