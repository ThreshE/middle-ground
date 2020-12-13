package com.cloud.mailcenter.service;

import com.cloud.mailcenter.config.EmailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RefreshScope
public class MailService {
    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private JavaMailSender mailSender;
    private AtomicInteger index = new AtomicInteger(0);

    /**
     * 轮询smtp邮件地址列表
     * @param
     * @return
     */
    private String select() {
        Map<String,Map<String,String>> mail = emailProperties.getMail();
        List<String> froms = new ArrayList<>();
        mail.keySet().forEach(key -> froms.add(mail.get(key).get("username")));
        if (index.get() >= froms.size()) {
            index.set(0);
        }
        String from = froms.get(index.getAndIncrement());
        mail.keySet().stream().filter(key -> mail.get(key).get("username").equals(from))
                .forEach(key -> {
                    if (mailSender instanceof JavaMailSenderImpl) {
                        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) mailSender;
                        mailSenderImpl.setHost(mail.get(key).get("host"));
                        mailSenderImpl.setUsername(mail.get(key).get("username"));
                        mailSenderImpl.setPassword(mail.get(key).get("password"));
                        mailSenderImpl.setProtocol(mail.get(key).get("protocol"));
                    }
                });
        return from;
    }
    /**
     * 发送简单邮件
     * @param to
     * @param subject
     * @param content
     */
    @Async
    public void sendSimpleMail(String to,String subject,
                               String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        String from = select();
        message.setFrom(from);
        mailSender.send(message);
        log.info("from:" + from + "到" + to + "的邮件发送成功!");
    }

    /**
     * 发送Html邮件
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    @Async
    public void sendHtmlMail(String to,String subject,
                             String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        String from = select();
        helper.setFrom(from);
        mailSender.send(message);
        log.info("from:" + from + "到" + to + "的邮件发送成功!");
    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws MessagingException
     */
    @Async
    public void sendAttachmentsMail(String to,String subject,
                                    String content,String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        String from = select();
        helper.setFrom(from);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName,file);
        mailSender.send(message);
        log.info("from:" + from + "到" + to + "的邮件发送成功!");
    }

    /**
     * 发送带图片的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @throws MessagingException
     */
    @Async
    public void sendInlineResourceMail(String to,String subject,
                                       String content,String rscPath,
                                       String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        String from = select();
        helper.setFrom(from);
        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId,res);
        mailSender.send(message);
        log.info("from:" + from + "到" + to + "的邮件发送成功!");
    }
}
