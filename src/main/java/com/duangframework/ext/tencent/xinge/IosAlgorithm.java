package com.duangframework.ext.tencent.xinge;

import com.duangframework.ext.push.IPushAlgorithm;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import com.duangframework.server.common.BootStrap;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *信鸽IOS算法
 * @author laotang
 * @date 2019/5/1
 */
public class IosAlgorithm implements IPushAlgorithm {

    private final static Logger logger = LoggerFactory.getLogger(IosAlgorithm.class);
    private static int env = XingeApp.IOSENV_DEV;
    private static IosAlgorithm INSTANCE = new IosAlgorithm();
    private static Integer DEVICE_TYPE = 0;

    public static IosAlgorithm getInstance() {
        return INSTANCE;
    }

    private IosAlgorithm(){
    }

    private int getIosEnv() {
        return BootStrap.getInstants().isDevModel() ? XingeApp.IOSENV_DEV : XingeApp.IOSENV_PROD;
    }

    private XingeApp getClient(){
        try {
            return XingeClient.getInstance().getClient();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * 推送消息到单个设备
     * @param pushRequest
     * @return
     */
    @Override
    public PushResponse pushSingleDevice(PushRequest pushRequest) {
        MessageIOS message = XingeUtils.convert2MessageIos(pushRequest);
        try {
            return XingeUtils.convert2Response(getClient().pushSingleDevice(pushRequest.getToken(), message, getIosEnv()));
        } catch (Exception e) {
            logger.warn("pushSingleDevice is fail: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public PushResponse pushSingleAccount(PushRequest pushRequest) {
        MessageIOS message = XingeUtils.convert2MessageIos(pushRequest);
        try {
            return XingeUtils.convert2Response(getClient().pushSingleAccount(DEVICE_TYPE, pushRequest.getAccount(), message, getIosEnv()));
        } catch (Exception e) {
            logger.warn("pushSingleAccount is fail: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public PushResponse pushAllDevice(PushRequest pushRequest) {
        MessageIOS message = XingeUtils.convert2MessageIos(pushRequest);
        try {
            return XingeUtils.convert2Response(getClient().pushAllDevice(DEVICE_TYPE, message, getIosEnv()));
        } catch (Exception e) {
            logger.warn("pushAllDevice is fail: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public PushResponse pushTags(List<String> tagsList, PushRequest pushRequest) {
        MessageIOS message = XingeUtils.convert2MessageIos(pushRequest);
        try {
            return XingeUtils.convert2Response(getClient().pushTags(DEVICE_TYPE, tagsList, "OR", message, getIosEnv()));
        } catch (Exception e) {
            logger.warn("pushTags is fail: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public PushResponse queryPushStatus(List<String> pushIdList) {
        try {
            return XingeUtils.convert2Response(getClient().queryPushStatus(pushIdList));
        } catch (Exception e) {
            logger.warn("queryPushStatus is fail: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查询设备数量
     * @return
     */
    @Override
    public PushResponse queryDeviceCount(){
        try {
            return XingeUtils.convert2Response(getClient().queryDeviceCount());
        } catch (Exception e) {
            logger.warn("queryDeviceCount is fail: " + e.getMessage(), e);
            return null;
        }
    }
}
