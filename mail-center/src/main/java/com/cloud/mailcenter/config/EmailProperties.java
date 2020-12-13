package com.cloud.mailcenter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "sender-email")
@Data
public class EmailProperties {
    private Map<String,Map<String,String>> mail;
}
