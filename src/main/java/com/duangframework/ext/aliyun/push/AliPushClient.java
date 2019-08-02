package com.duangframework.ext.aliyun.push;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.duangframework.ext.ConstEnum;
import com.duangframework.ext.push.IPush;
import com.duangframework.ext.push.IPushAlgorithm;
import com.duangframework.kit.ToolsKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AliPushClient implements IPush<IAcsClient> {

    private static final Logger logger = LoggerFactory.getLogger(AliPushClient.class);

    private static Lock lock = new ReentrantLock();
    private static IAcsClient pushClient;
    private static AliPushClient INSTANCE;

    public static AliPushClient getInstance() {
        try {
            lock.lock();
            if (ToolsKit.isEmpty(INSTANCE)) {
                INSTANCE = new AliPushClient();
            }
        } catch (Exception e) {
            logger.warn("AliPushClient getInstance is fail: " + e.getMessage(), e);
        }finally {
            lock.unlock();
        }
        return INSTANCE;
    }

    private AliPushClient(){

    }

    @Override
    public IAcsClient getClient() throws Exception {
        if(ToolsKit.isEmpty(pushClient)) {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ConstEnum.ALIYUN.ACCESS_KEY_ID.getValue(), ConstEnum.ALIYUN.ACCESS_KEY_SECRET.getValue());
            pushClient = new DefaultAcsClient(profile);
        }
        return pushClient;
    }

    @Override
    public boolean isSuccess() {
        return null != pushClient;
    }

    @Override
    public IPushAlgorithm getPushAlgorithm(boolean isAndroid) {
        return null;
    }
}
