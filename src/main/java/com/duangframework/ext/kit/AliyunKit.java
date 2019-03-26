package com.duangframework.ext.kit;

import com.duangframework.ext.aliyun.oss.OssUtils;
import com.duangframework.kit.ToolsKit;

/**
 *  阿里云工具类
 *
 * @author laotang
 * @since 1.0
 * @date 2019-03-22
 */
public class AliyunKit {

    private static OssUtils ossUtils = null;

    public static OssUtils oss() {
        if(ToolsKit.isEmpty(ossUtils)) {
            ossUtils = OssUtils.getInstance();
        }
        return ossUtils;
    }

}
