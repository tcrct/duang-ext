package com.duangframework.ext;

public enum ConstEnum {;

    public enum  ALIYUN {
        ACCESS_KEY_ID("LTAID4F8gZO7HA4P","APP KEY"),
        ACCESS_KEY_SECRET("7fe6lJZwfC7bsgS6Y6eCBobWAGY50n","APP SECRET"),

        // OSS
        OSS_ENDPOINT("oss-cn-hangzhou.aliyuncs.com","OSS的链接地址"),


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
