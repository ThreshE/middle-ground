package com.cloud.business.alibaba.product.param;

/**
 *
 * @description:
 * @author: 廖权名
 * @date: 2020/9/10 18:21
 */
public class AlibabaProductDeliverySubTemplateDetailDTO {

    private AlibabaProductDeliverySubTemplateDTO subTemplateDTO;

    /**
     * @return 子模板
     */
    public AlibabaProductDeliverySubTemplateDTO getSubTemplateDTO() {
        return subTemplateDTO;
    }

    /**
     * 设置子模板     *
     * 参数示例：<pre>{}</pre>     
     * 此参数必填
     */
    public void setSubTemplateDTO(AlibabaProductDeliverySubTemplateDTO subTemplateDTO) {
        this.subTemplateDTO = subTemplateDTO;
    }

    private AlibabaProductDeliveryRateDetailDTO[] rateList;

    /**
     * @return 费率
     */
    public AlibabaProductDeliveryRateDetailDTO[] getRateList() {
        return rateList;
    }

    /**
     * 设置费率     *
     * 参数示例：<pre>[]</pre>     
     * 此参数必填
     */
    public void setRateList(AlibabaProductDeliveryRateDetailDTO[] rateList) {
        this.rateList = rateList;
    }

}
