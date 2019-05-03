package com.duangframework.ext.tencent.xinge;

import com.duangframework.ext.ConstEnum;
import com.duangframework.ext.push.IPush;
import com.duangframework.ext.push.IPushAlgorithm;
import com.duangframework.kit.ToolsKit;
import com.tencent.xinge.XingeApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author laotang
 * @date 2019/5/1
 */
public class XingeClient implements IPush<XingeApp> {

    private final static Logger logger = LoggerFactory.getLogger(XingeClient.class);

    private static XingeClient INSTANCE = new XingeClient();
    private  XingeApp client;

    public static XingeClient getInstance() {
        return INSTANCE;
    }

    public XingeClient(){
    }

    @Override
    public XingeApp getClient() throws Exception {
        if(ToolsKit.isEmpty(client)) {
            /** 推送目标应用 id, 推送密钥*/
            client = new XingeApp(Long.parseLong(ConstEnum.XINGE.ACCESS_KEY_ID.getValue()),ConstEnum.XINGE.ACCESS_KEY_SECRET.getValue());
        }
        return client;
    }

    @Override
    public boolean isSuccess() {
        return null != client;
    }

    /**
     *
     * @param isAndroid 是否安桌系统的手机*
     * @return
     */
    @Override
    public IPushAlgorithm getPushAlgorithm(boolean isAndroid) {
        return isAndroid  ? AndroidAlgorithm.getInstance() : IosAlgorithm.getInstance();
    }
}
