package com.duangframework.ext.kit;

import com.duangframework.ext.aliyun.oss.OssUtils;

/**
 *  阿里云工具类
 *
 * @author laotang
 * @since 1.0
 * @date 2019-03-22
 */
public class AliyunKit {

    private static AliyunKit _aliyunKit = new AliyunKit();

    private static OssUtils ossUtils = null;
    private AliyunKit() {
        ossUtils = OssUtils.getInstance(); // 存储对象
    }

    public static OssUtils oss() {
        return ossUtils;
    }

}
