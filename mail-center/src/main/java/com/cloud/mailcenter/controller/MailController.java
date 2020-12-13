package com.cloud.mailcenter.controller;

import com.cloud.common.result.Result;
import com.cloud.mailcenter.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailController {
    @Autowired
    private MailService mailService;

    @GetMapping("/sendsimplemail")
    @SuppressWarnings("unchecked")
    public Result<String> sendSimpleMail(@RequestParam String to, @RequestParam String subject,
                                         @RequestParam String content) {
        mailService.sendSimpleMail(to,subject,content);
        return Result.success("ok");
    }

    @GetMapping("/sendhtmlmail")
    @SuppressWarnings("unchecked")
    public Result<String> sendHtmlMail(@RequestParam String to,@RequestParam String subject,
                                       @RequestParam String content) throws MessagingException {
        mailService.sendHtmlMail(to,subject,content);
        return Result.success("ok");
    }

    @GetMapping("/sendattachmentsmail")
    @SuppressWarnings("unchecked")
    public Result<String> sendAttachmentsMail(@RequestParam String to,@RequestParam String subject,
                                              @RequestParam String content,@RequestParam String filePath) throws MessagingException {
        mailService.sendAttachmentsMail(to,subject,content,filePath);
        return Result.success("ok");
    }

    @GetMapping("/sendinlineresourcemail")
    @SuppressWarnings("unchecked")
    public Result<String> sendInlineResourceMail(@RequestParam String to,@RequestParam String subject,
                                                 @RequestParam String content,@RequestParam String rscPath,
                                                 @RequestParam String rscId) throws MessagingException {
        mailService.sendInlineResourceMail(to,subject,content,rscPath,rscId);
        return Result.success("ok");
    }
}
