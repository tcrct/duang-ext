package com.duangframework.ext.push;

import com.duangframework.mvc.annotation.Bean;
import com.duangframework.vtor.annotation.Length;
import com.duangframework.vtor.annotation.NotEmpty;
import com.duangframework.vtor.annotation.Range;
import com.tencent.xinge.TimeInterval;

/**
 * 发送信息实体类
 * @author  laotang
 * @date 2019/5/2.
 */
@Bean
public class PushRequest implements java.io.Serializable {

    /**
     * 帐号
     */
    private String account;
    /**
     * 令牌
     */
    private String token;
    /**标题，标题+内容不得超过 800 英文字符或 400 非英文字符*/
    @NotEmpty
    @Length(value = 400)
    private String title;
    /**内容，标题+内容不得超过 800 英文字符或 400*/
    @NotEmpty
    @Length(value = 400)
    private String content;
    /**消息离线存储多久，单位为秒，默认存储时间 3 天。*/
    private Integer expireTime = 60 * 60 * 24 * 3;
    /**消息定时推送的时间，格式为 year-mon-day hour:min:sec，若小于服务器当前时间则立即推送。选填，默认为空字符串，代表立即推送*/
    private String sendTime;
    /** 元素为 TimeInterval 类型，表示允许推送的时间段，选填*/
    private TimeInterval acceptTime = new TimeInterval(0, 0, 23, 59);
    /** 消息类型。0:通知； 1:透传消息。必填*/
    @Range(value = {0, 1})
    private Integer type = 0;
    /**是否安卓手机，默认为true*/
    private boolean isAndroid = true;
    /**点击打开的链接地址*/
    private String targetUrl;


    public PushRequest() {
    }

    public PushRequest(String account, String title, String content) {
        this.account = account;
        this.title = title;
        this.content = content;
    }
    public PushRequest(String token, String account, String title, String content) {
        this.token = token;
        this.account = account;
        this.title = title;
        this.content = content;
    }

    public PushRequest(String token, String title, String content, Integer expireTime, String sendTime, TimeInterval acceptTime, Integer type, String targetUrl) {
        this.token = token;
        this.title = title;
        this.content = content;
        this.expireTime = expireTime;
        this.sendTime = sendTime;
        this.acceptTime = acceptTime;
        this.type = type;
        this.targetUrl = targetUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public TimeInterval getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(TimeInterval acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isAndroid() {
        return isAndroid;
    }

    public void setAndroid(boolean android) {
        isAndroid = android;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public String toString() {
        return "PushRequest{" +
                "account='" + account + '\'' +
                "token='" + token + '\'' +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", expireTime=" + expireTime +
                ", sendTime='" + sendTime + '\'' +
                ", acceptTime=" + acceptTime +
                ", type=" + type +
                ", isAndroid=" + isAndroid +
                ", targetUrl=" + targetUrl +
                '}';
    }
}
