package com.cloud.business.castlers.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @description:
 * @author: 廖权名
 * @date: 2020/9/10 18:21
 */
@Data
public class ApiCallRecords {
    private String id;

    private String requestParameters;

    private String ip;

    private String contentType;

    private LocalDateTime callTime=LocalDateTime.now();

    private String responseParameters;

    private Integer successFlag;

}
