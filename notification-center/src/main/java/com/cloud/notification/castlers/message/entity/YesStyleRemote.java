package com.cloud.notification.castlers.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class YesStyleRemote {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private Integer statusCode;

    private Integer type;

    private String  message;

    private String description;

    private String url;

    private LocalDateTime createTime = LocalDateTime.now();

}
