package com.cloud.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "skill")
public class NameConf {
    private String[] name;
}
