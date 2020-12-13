package com.cloud.model.mail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件
 *
 * @author 关键
 */
@Data
public class Mail implements Serializable {

    private static final long serialVersionUID = 4885093464493499448L;

    private Long id;
    private Long userId;
    private String username;
    private String toEmail;
    private String subject;
    private String content;
    private Integer status;
    private Date sendTime;
    private Date createTime;
    private Date updateTime;

}
