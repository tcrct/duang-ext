package com.duangframework.ext.kit;

import com.duangframework.ext.aliyun.dns.DnsUtils;
import com.duangframework.ext.aliyun.oss.OssUtils;
import com.duangframework.ext.aliyun.sms.SmsUtils;
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
    private static SmsUtils smsUtils = null;
    private static DnsUtils dnsUtils = null;

    public static OssUtils oss() {
        if(ToolsKit.isEmpty(ossUtils)) {
            ossUtils = OssUtils.getInstance();
        }
        return ossUtils;
    }

    public static SmsUtils sms() {
        if(ToolsKit.isEmpty(smsUtils)) {
            smsUtils = SmsUtils.getInstance();
        }
        return smsUtils;
    }

    public static DnsUtils dns() {
        if(ToolsKit.isEmpty(dnsUtils)) {
            dnsUtils = DnsUtils.getInstance();
        }
        return dnsUtils;
    }

}
