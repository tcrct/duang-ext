package com.duangframework.ext;

public enum ConstEnum {;

    public enum  ALIYUN {
        ACCESS_KEY_ID("","APP KEY"),
        ACCESS_KEY_SECRET("","APP SECRET"),

        // OSS
        OSS_ENDPOINT("","OSS的链接地址"),


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
