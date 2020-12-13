package com.cloud.business.alibaba.product.param;

import com.cloud.business.alibaba.ocean.rawsdk.client.APIId;
import com.cloud.business.alibaba.ocean.rawsdk.common.AbstractAPIRequest;

/**
 *
 * @description:
 * @author: 廖权名
 * @date: 2020/9/10 18:21
 */
public class AlibabaCpsMediaProductInfoParam extends AbstractAPIRequest<AlibabaCpsMediaProductInfoResult> {

    public AlibabaCpsMediaProductInfoParam() {
        super();
        oceanApiId = new APIId("com.alibaba.product", "alibaba.cpsMedia.productInfo", 1);
    }

    private Long offerId;

    /**
     * @return 1688商品ID，等同于productId
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * 设置1688商品ID，等同于productId     *
     * 参数示例：<pre>573741401425</pre>     
     * 此参数必填
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private Boolean needCpsSuggestPrice;

    /**
     * @return 是否需要CPS建议价
     */
    public Boolean getNeedCpsSuggestPrice() {
        return needCpsSuggestPrice;
    }

    /**
     * 设置是否需要CPS建议价     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setNeedCpsSuggestPrice(Boolean needCpsSuggestPrice) {
        this.needCpsSuggestPrice = needCpsSuggestPrice;
    }

    private Boolean needIntelligentInfo;

    /**
     * @return 是否返回算法改写的信息，包括标题、图片和详情图片
     */
    public Boolean getNeedIntelligentInfo() {
        return needIntelligentInfo;
    }

    /**
     * 设置是否返回算法改写的信息，包括标题、图片和详情图片     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setNeedIntelligentInfo(Boolean needIntelligentInfo) {
        this.needIntelligentInfo = needIntelligentInfo;
    }

}
