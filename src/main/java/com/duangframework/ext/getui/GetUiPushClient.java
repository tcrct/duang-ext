package com.duangframework.ext.getui;

import com.duangframework.ext.ConstEnum;
import com.duangframework.ext.push.IPush;
import com.duangframework.ext.push.IPushAlgorithm;
import com.duangframework.kit.ToolsKit;
import com.gexin.rp.sdk.http.IGtPush;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GetUiPushClient implements IPush<IGtPush> {

    private static final Logger logger = LoggerFactory.getLogger(GetUiPushClient.class);

    private static Lock lock = new ReentrantLock();
    private static IGtPush pushClient;
    private static GetUiPushClient INSTANCE;

    public static GetUiPushClient getInstance() {
        try {
            lock.lock();
            if (ToolsKit.isEmpty(INSTANCE)) {
                INSTANCE = new GetUiPushClient();
            }
        } catch (Exception e) {
            logger.warn("AliPushClient getInstance is fail: " + e.getMessage(), e);
        }finally {
            lock.unlock();
        }
        return INSTANCE;
    }

    private GetUiPushClient(){

    }

    @Override
    public IGtPush getClient() throws Exception {
        if(ToolsKit.isEmpty(pushClient)) {
            pushClient = new IGtPush(ConstEnum.GETUI.URl.getValue(), ConstEnum.GETUI.ACCESS_KEY.getValue(), ConstEnum.GETUI.MASTER_SECRET.getValue());
        }
        return pushClient;
    }

    @Override
    public boolean isSuccess() {
        return null != pushClient;
    }

    @Override
    public IPushAlgorithm getPushAlgorithm(boolean isAndroid) {
        return GetUiPushAlgorithm.getInstance();
    }
}
