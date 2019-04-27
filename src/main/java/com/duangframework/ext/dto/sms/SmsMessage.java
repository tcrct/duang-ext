package com.duangframework.ext.dto.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmsMessage implements java.io.Serializable {

    private List<String> phones = new ArrayList<>();
    private String templateCode;
    private Map<String, String> templateParam = new HashMap<>();

    public SmsMessage() {
    }

    public SmsMessage(List<String> phones, String templateCode, Map<String, String> templateParam) {
        this.phones = phones;
        this.templateCode = templateCode;
        this.templateParam = templateParam;
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


}
