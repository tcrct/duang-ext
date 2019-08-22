package com.duangframework.ext.dto.sms;

/**
 * 短信模板中的APPLink自定义Dto
 * @author laotang
 */
public class SmsLinkDto implements java.io.Serializable {

    /** 短信模板中的APPLink链接地址 **/
    private String url;

    /** 短信模板中的APPLink自定义字段**/
    private String payload;

    /** 离线多久后进行补发的时间,毫秒为单位*/
    private Long offlineSendtime = 10000L;

    public SmsLinkDto() {
    }

    public SmsLinkDto(String url, String payload, Long offlineSendtime) {
        this.url = url;
        this.payload = payload;
        this.offlineSendtime = offlineSendtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Long getOfflineSendtime() {
        return offlineSendtime;
    }

    public void setOfflineSendtime(Long offlineSendtime) {
        this.offlineSendtime = offlineSendtime;
    }

    @Override
    public String toString() {
        return "SmsLinkDto{" +
                "url='" + url + '\'' +
                ", payload='" + payload + '\'' +
                ", offlineSendtime=" + offlineSendtime +
                '}';
    }
}
