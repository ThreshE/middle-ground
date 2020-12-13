package com.cloud.notification.castlers.message.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubMessageModel {

    private String msgId;
    private String gmtBorn;
    private Object data;
    private String userInfo;
    private String type;

}
