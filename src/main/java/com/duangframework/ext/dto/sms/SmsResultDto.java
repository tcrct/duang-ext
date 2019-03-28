package com.duangframework.ext.dto.sms;

/**
 * 发送短信后返回的内容消息
 */
public class SmsResultDto implements java.io.Serializable {

    private String Message;
    private String RequestId;
    private String BizId;
    private String Code;

    public SmsResultDto() {
    }

    public SmsResultDto(String message, String requestId, String bizId, String code) {
        Message = message;
        RequestId = requestId;
        BizId = bizId;
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getBizId() {
        return BizId;
    }

    public void setBizId(String bizId) {
        BizId = bizId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
