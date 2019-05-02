package com.duangframework.ext.tencent.xinge;

import ch.qos.logback.classic.jmx.MBeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.duangframework.ext.push.IPush;
import com.duangframework.ext.push.PushMessage;
import com.duangframework.kit.ToolsKit;
import com.duangframework.mvc.core.helper.BeanHelper;
import com.tencent.xinge.XingeApp;
import com.tencent.xinge.bean.Message;

/**
 * Created by laotang on 2019/5/1.
 */
public class XingeUtils implements IPush<XingeApp> {

    public XingeApp client;
    // 推送目标应用 id
    public static final String accessId = "";
    // 推送密钥
    public static final String secretKey = "";


    @Override
    public XingeApp getClient() throws Exception {
        if(ToolsKit.isEmpty(client)) {
            client = new XingeApp(accessId,secretKey);
        }
        return client;
    }

    @Override
    public boolean isSuccess() {
        return null != client;
    }

    @Override
    public JSONObject pushSingleDevice(PushMessage message) {
        Message message = new Message();

        return getClient().pushSingleDevice();
    }
}
