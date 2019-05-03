package com.duangframework.ext.push;

import com.duangframework.ext.tencent.xinge.XingeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author laotang
 * @date 2019/5/3
 */
public class PushFactory {

    private final static Logger logger = LoggerFactory.getLogger(PushFactory.class);
    /**推送算法对象*/
    private static IPush push;

    /**
     * 取推送算法
     * @param isAndroid 是否安桌系统的手机*
     * @return
     */
    private static IPushAlgorithm getPushAlgorithm(boolean isAndroid) {
        if(null == push) {
            // 如需更改推送厂商，实现对应的手机端推送API后，再更改以下代码，其余代码不需要改变
            push = new XingeClient();
        }
        return push.getPushAlgorithm(isAndroid);
    }

    public static PushResponse pushSingleDevice(PushRequest pushRequest) {
        try {
            return getPushAlgorithm(pushRequest.isAndroid()).pushSingleDevice(pushRequest);
        } catch (Exception e) {
            logger.warn("pushSingleDevice is fail: " + e.getMessage(), e);
            return null;
        }
    }

    public static PushResponse pushSingleAccount(PushRequest pushRequest) {
        try {
            return getPushAlgorithm(pushRequest.isAndroid()).pushSingleAccount(pushRequest);
        } catch (Exception e) {
            logger.warn("pushSingleAccount is fail: "+e.getMessage(), e);
            return null;
        }
    }

    public static PushResponse pushAllDevice(boolean isAndroid) {
        try {
            return getPushAlgorithm(isAndroid).pushAllDevice();
        } catch (Exception e) {
            logger.warn("pushAllDevice is fail: "+e.getMessage(), e);
            return null;
        }
    }

    public static PushResponse pushTags(boolean isAndroid, List<String> tagsList) {
        try {
            return getPushAlgorithm(isAndroid).pushTags(tagsList);
        } catch (Exception e) {
            logger.warn("pushTags is fail: "+e.getMessage(), e);
            return null;
        }
    }

    public static PushResponse queryPushStatus(boolean isAndroid, List<String> idList) {
        try {
            return getPushAlgorithm(isAndroid).queryPushStatus(idList);
        } catch (Exception e) {
         logger.warn("queryPushStatus is fail: "+e.getMessage(), e);
         return null;
        }
    }

    public PushResponse queryDeviceCount(boolean isAndroid){
        try {
            return getPushAlgorithm(isAndroid).queryDeviceCount();
        } catch (Exception e) {
            logger.warn("queryPushStatus is fail: "+e.getMessage(), e);
            return null;
        }
    }
}
