package com.cloud.business.castlers.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @description:
 * @author: 廖权名
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlibabaCpsMediaProductInfoParamRequest {

    @ApiModelProperty(value = "设置1688商品ID，等同于productId  参数示例：<pre>573741401425</pre>  ",required = true)
    private Long offerId;

    @ApiModelProperty("设置是否需要CPS建议价 参数示例：<pre>true</pre> ")
    private Boolean needCpsSuggestPrice;

    @ApiModelProperty("是否返回算法改写的信息，包括标题、图片和详情图片 参数示例：<pre>true</pre>")
    private Boolean needIntelligentInfo;


}
