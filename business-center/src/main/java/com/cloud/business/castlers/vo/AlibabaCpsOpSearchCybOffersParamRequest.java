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
public class AlibabaCpsOpSearchCybOffersParamRequest {

    @ApiModelProperty(value = "设置枚举;经营模式;1:生产加工,2:经销批发,3:招商代理,4:商业服务  参数示例：<pre>2</pre>    ")
    private String biztype;

    @ApiModelProperty(value = "设置枚举;买家保障,多个值用逗号分割;qtbh:7天包换;swtbh:15天包换 参数示例：<pre>qtbh</pre>  ")
    private String buyerProtection;

    @ApiModelProperty(value = "设置所在地区- 市  参数示例：<pre>杭州</pre>")
    private String city;

    @ApiModelProperty(value = "设置枚举;发货时间;1:24小时发货;2:48小时发货;3:72小时发货 .参数示例：<pre>3</pre> ")
    private String deliveryTimeType;

    @ApiModelProperty(value = "是否倒序;正序: false;倒序:true 参数示例：<pre>true</pre>    ")
    private Boolean descendOrder;

    @ApiModelProperty(value = "设置商品售卖类型筛选;枚举,多个值用分号分割;免费赊账:50000114;参数示例：<pre>50000114</pre>   ")
    private String holidayTagId;

    @ApiModelProperty(value = "设置搜索关键词,参数示例：<pre>女装</pre>  ")
    private String keyWords;

    @ApiModelProperty(value = "设置页码 参数示例：<pre>1</pre>", required = true)
    private Integer page;

    @ApiModelProperty(value = "设置页面数量;最大20 参数示例：<pre>10</pre>", required = true)
    private String pageSize;

    @ApiModelProperty(value = "设置类目id;4 纺织、皮革 5 电工电气 10 能源 12 交通运输 16 医药、保养 17 工艺品、礼品 57 电子元器件 58 照明工业 64 环保 66 医药、保养 67 办公、文教 69 商务服务 96 家纺家饰 311 童装 312 内衣 1813 玩具 2805 加工 2829 二手设备转让 10165 男装 1038378 鞋 1042954 箱包皮具 127380009 运动服饰 130822002 餐饮生鲜 130823000 性保健品 200514001 床上用品 201128501 直播 1 农业 2 食品酒水 7 数码、电脑 9 冶金矿产 15 日用百货 18 运动装备 33 汽摩及配件 53 传媒、广电 54 服饰配件、饰品 59 五金、工具 68 包装 70 安全、防护 96 家居饰品 97 美妆日化 97 美容护肤/彩妆 1501 母婴用品 10166 女装 10208 仪器仪表 122916001 宠物及园艺 123614001 钢铁 130822220 个护/家清 6 家用电器 8 化工 13 家装、建材 21 办公、文教 55 橡塑 65 机械及行业设备 71 汽摩及配件 72 印刷 73 项目合作 509 通信产品 1426 机床 1043472 毛巾、巾类 122916002 汽车用品" +
            "参数示例：<pre>122916002</pre>     ")
    private Long postCategoryId;

    @ApiModelProperty(value = "设置最低价 参数示例：<pre>10.2</pre>   ")
    private Double priceStart;

    @ApiModelProperty(value = "设置最高价 参数示例：<pre>11.2</pre>   ")
    private Double priceEnd;

    @ApiModelProperty(value = "设置价格类型;默认分销价;agent_price:分销价; 参数示例：<pre>agent_price</pre>      ")
    private String priceFilterFields;

    @ApiModelProperty(value = "设置所在地区- 省 参数示例：<pre>浙江</pre>   ")
    private String province;

    @ApiModelProperty(value = "设置枚举;排序字段;normal:综合; 参数示例：<pre>saleQuantity</pre>    ")
    private String sortType;

    @ApiModelProperty(value = "设置历史遗留，可不用 参数示例：<pre>266818</pre> ")
    private String tags;

    @ApiModelProperty(value = "设置枚举;1387842:渠道专享价商品  参数示例：<pre>1387842</pre>    ")
    private String offerTags;

    @ApiModelProperty(value = "设置商品id搜索，多个id用逗号分割  参数示例：<pre>600335270178</pre> ")
    private String offerIds;

}
