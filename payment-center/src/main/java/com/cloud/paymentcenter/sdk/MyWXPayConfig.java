package com.cloud.paymentcenter.sdk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
@Slf4j
public class MyWXPayConfig extends WXPayConfig {
    @Value("${application.wxpay.appId}")
    private String appId;

    @Value("${application.wxpay.mchId}")
    private String mchId;

    @Value("${application.wxpay.key}")
    private String key;

    @Value("${application.wxpay.certPath}")
    private String certPath;

    @Value("${application.wxpay.payNotifyUrl}")
    private String payNotifyUrl;

    private byte[] certData;

    @PostConstruct
    public void init() throws Exception{
        Resource resource = new ClassPathResource(certPath);
        File file = resource.getFile();
        FileInputStream in = new FileInputStream(file);
        BufferedInputStream bin = new BufferedInputStream(in);
        this.certData = new byte[(int)file.length()];
        bin.read(this.certData);
        bin.close();
        in.close();
    }

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream in=new ByteArrayInputStream(this.certData);
        return in;
    }

    @Override
    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API,true);
            }
        };

    }
}
