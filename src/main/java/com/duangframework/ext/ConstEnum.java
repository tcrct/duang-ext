package com.duangframework.ext;

public enum ConstEnum {;

    public enum  ALIYUN {
        ACCESS_KEY_ID("","APP KEY"),
        ACCESS_KEY_SECRET("","APP SECRET"),

        // DNS
        DNS_REGION_ID ("cn-hangzhou","dns必填固定值，必须为cn-hanghou"),

        // OSS
        OSS_ENDPOINT("oss-cn-hangzhou.aliyuncs.com","OSS的链接地址"),

        // SMS
        SMS_PHONE_NUMBER_FIELD ("PhoneNumbers","手机号码字段名"),
        SMS_SIGN_NAME_FIELD("SignName","签名名称字段名"),
        SMS_CODE_FIELD("TemplateCode","短信模板字段名"),
        SMS_PARAM_FIELD("TemplateParam","短信参数字段名"),
        SMS_PHONE_NUMBER_JSON_FIELD("PhoneNumberJson","手机号码数组字组名"),
        SMS_SENDSMS_FIELD("SendSms","发送一条短信"),
        SMS_SEND_BATCH_SMS_FIELD("SendBatchSms","批量发送短信"),
        SMS_SIGN_NAME("思格特智能印章系统","签名名称"),
        SMS_DOMAIN("dysmsapi.aliyuncs.com","短信域名"),
        SMS_VERSION("2017-05-25","短信版本号"),

        //

        ;private final String value;private final String desc;
        ALIYUN(String value, String desc) {this.value = value;this.desc = desc;}
        public String getValue() {return value;}public String getDesc() {return desc;}
    }

    public enum  QINIU {
        ACCESS_KEY_ID("",""),
        ACCESS_KEY_SECRET("",""),


        ;private final String value;private final String desc;
        QINIU(String value, String desc) {this.value = value;this.desc = desc;}
        public String getValue() {return value;}public String getDesc() {return desc;}
    }

}
