package com.duangframework.ext.tencent.xinge;

import com.duangframework.ext.IClient;
import com.duangframework.ext.push.IPushAlgorithm;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import com.duangframework.kit.ToolsKit;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author laotang
 * @date 2019/5/1
 */
public class AndroidAlgorithm implements IPushAlgorithm {

    private final static Logger logger = LoggerFactory.getLogger(AndroidAlgorithm.class);

    private static AndroidAlgorithm INSTANCE = new AndroidAlgorithm();
    private static Integer DEVICE_TYPE = 0;

    public static AndroidAlgorithm getInstance() {
        return INSTANCE;
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
        Message message = XingeUtils.convert2Message(pushRequest);
        try {
            JSONObject jsonObject =  getClient().pushSingleDevice(pushRequest.getToken(), message);
            return XingeUtils.convert2Response(jsonObject);
        } catch (Exception e) {
            logger.warn("pushSingleDevice is fail: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public PushResponse pushSingleAccount(PushRequest pushRequest) {
        Message message = XingeUtils.convert2Message(pushRequest);
        message.setType(Message.TYPE_MESSAGE);
        try {
            JSONObject jsonObject =  getClient().pushSingleAccount(DEVICE_TYPE, pushRequest.getAccount(), message);
            return XingeUtils.convert2Response(jsonObject);
        } catch (Exception e) {
            logger.warn("pushSingleAccount is fail: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public PushResponse pushAllDevice() {
        Message message = XingeUtils.convert2Message(null);
        try {
            return XingeUtils.convert2Response(getClient().pushAllDevice(DEVICE_TYPE, message));
        } catch (Exception e) {
            logger.warn("pushAllDevice is fail: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public PushResponse pushTags(List<String> tagList) {
        Message message = XingeUtils.convert2Message(null);
        try {
            return XingeUtils.convert2Response(getClient().pushTags(DEVICE_TYPE, tagList, "OR", message));
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
