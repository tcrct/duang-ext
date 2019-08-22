package com.duangframework.ext.dto.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送短信内容对象
 * @author laotang
 */
public class SmsMessage implements java.io.Serializable {

    /**接收都的电话号码集合*/
    private List<String> phones = new ArrayList<>();
    /**短信模板ID 需要在个推报备开通 才可使用*/
    private String templateCode;
    /**模板中占位符的内容k.v 结构*/
    private Map<String, String> templateParam = new HashMap<>();
    /**短信链接对象Dto*/
    private SmsLinkDto smsLinkDto;

    public SmsMessage() {
    }

    public SmsMessage(List<String> phones, String templateCode, Map<String, String> templateParam) {
        this(phones, templateCode, templateParam, null);
    }

    public SmsMessage(List<String> phones, String templateCode, Map<String, String> templateParam, SmsLinkDto smsLinkDto) {
        this.phones = phones;
        this.templateCode = templateCode;
        this.templateParam = templateParam;
        this.smsLinkDto = smsLinkDto;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones.addAll(phones);
    }
    public void setPhone(String phone) {
        this.phones.add(phone);
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, String> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map<String, String> templateParam) {
        this.templateParam.putAll(templateParam);
    }

    public SmsMessage setSmsParam(String key, String value) {
        this.templateParam.put(key, value);
        return this;
    }

    public SmsLinkDto getSmsLinkDto() {
        return smsLinkDto;
    }

    public void setSmsLinkDto(SmsLinkDto smsLinkDto) {
        this.smsLinkDto = smsLinkDto;
    }
}
