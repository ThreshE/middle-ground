//package com.cloud.paymentcenter.config;
//
//import com.github.wxpay.sdk.WXPayConfig;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//
//@Component
//@ConfigurationProperties(prefix = "application.wxpay")
//public class WxPayAppConfig implements WXPayConfig{
//    /**
//     * appID
//     */
//    @Setter
//    private String appID;
//
//    /**
//     * 商户号
//     */
//    @Setter
//    private String mchID;
//
//    /**
//     * API 密钥
//     */
//    @Setter
//    private String key;
//
//    /**
//     * API证书绝对路径 (本项目放在了 resources/cert/wxpay/apiclient_cert.p12")
//     */
//    @Getter
//    @Setter
//    private String certPath;
//
//    /**
//     * HTTP(S) 连接超时时间，单位毫秒
//     */
//    @Setter
//    private int httpConnectTimeoutMs = 8000;
//
//    /**
//     * HTTP(S) 读数据超时时间，单位毫秒
//     */
//    @Setter
//    private int httpReadTimeoutMs = 10000;
//
//    /**
//     * 微信支付异步通知地址
//     */
//    @Setter
//    @Getter
//    private String payNotifyUrl;
//
//    /**
//     * 微信退款异步通知地址
//     */
//    @Setter
//    @Getter
//    private String refundNotifyUrl;
//
//    @Override
//    public String getAppID() {
//        return appID;
//    }
//
//    @Override
//    public String getMchID() {
//        return mchID;
//    }
//
//    @Override
//    public String getKey() {
//        return key;
//    }
//
//    @Override
//    public InputStream getCertStream() {
//        InputStream certStream = getClass().getClassLoader().getResourceAsStream(certPath);
//        return certStream;
//    }
//
//    @Override
//    public int getHttpConnectTimeoutMs() {
//        return httpConnectTimeoutMs;
//    }
//
//    @Override
//    public int getHttpReadTimeoutMs() {
//        return httpReadTimeoutMs;
//    }
//}
