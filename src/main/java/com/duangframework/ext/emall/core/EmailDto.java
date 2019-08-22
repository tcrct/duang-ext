package com.duangframework.ext.emall.core;

import com.duangframework.mvc.annotation.Param;

import java.util.HashSet;
import java.util.Set;

/**
 * 邮件发送Dto
 * @author laotang
 */
public class EmailDto {

    @Param(label = "主题")
    private String subject;
    @Param(label = "接收人集合")
    private Set<String> receivers = new HashSet<>();
    @Param(label = "发送的内容")
    private String body;
    @Param(label = "邮件类型枚举")
    private EmailType typeEnum;

    public EmailDto() {
    }

    public EmailDto(String subject, Set<String> receivers, String body, EmailType typeEnum) {
        this.subject = subject;
        this.receivers = receivers;
        this.body = body;
        this.typeEnum = typeEnum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<String> receivers) {
        this.receivers = receivers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailType getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(EmailType typeEnum) {
        this.typeEnum = typeEnum;
    }
}
