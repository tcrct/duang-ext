package com.duangframework.ext.aliyun.push;

import com.aliyuncs.IAcsClient;
import com.duangframework.ext.push.IPushAlgorithm;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 阿里云推送(Android/iOS)封装
 * @author laotang
 */
public class AliyunPushAlgorithm implements IPushAlgorithm {

    private final static Logger logger = LoggerFactory.getLogger(AliyunPushAlgorithm.class);

    private static AliyunPushAlgorithm INSTANCE = new AliyunPushAlgorithm();
    private static Integer RET_SUCCESS_CODE = 0;
    private static Integer RET_ERROR_CODE = 1;
    private static String RET_SUCCESS = "success";
    private static String RET_ERROR = "error";

    public static AliyunPushAlgorithm getInstance() {
        return INSTANCE;
    }

    private IAcsClient getClient(){
        try {
            return AliPushClient.getInstance().getClient();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    private PushResponse push(String target) {
        try{
            return push(target, null);
        }catch (Exception e) {
            throw e;
        }
    }
    private PushResponse push(String target, PushRequest pushRequest) {
        try {
            com.aliyuncs.push.model.v20160801.PushResponse aliyunPushResponse = getClient().getAcsResponse(
                    AliPushUtils.createAliyunPushRequest(target, pushRequest));
            logger.warn("RequestId: %s, MessageID: %s\n", aliyunPushResponse.getRequestId(), aliyunPushResponse.getMessageId());
            return new PushResponse(RET_SUCCESS_CODE, RET_SUCCESS, "");
        } catch (Exception e) {
            return new PushResponse(RET_ERROR_CODE, RET_ERROR, e.getMessage());
        }
    }

    @Override
    public PushResponse pushSingleDevice(PushRequest pushRequest) {
        try {
            return push("DEVICE", pushRequest);
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public PushResponse pushSingleAccount(PushRequest pushRequest) {
        try {
            return push("ACCOUNT", pushRequest);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PushResponse pushAllDevice() {
        try {
            return push("DEVICE");
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PushResponse pushTags(List<String> tagsList) {
        try {
            return push("TAG");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 没实现
     * https://github.com/aliyun/alicloud-ams-demo/blob/master/OpenApi2.0/push-openapi-java-demo/src/test/java/com/aliyun/push/demoTest/StatTest.java
     * @param pushIdList
     * @return
     */
    @Override
    public PushResponse queryPushStatus(List<String> pushIdList) {
        return null;
    }

    /**
     * 没实现
     * @return
     */
    @Override
    public PushResponse queryDeviceCount() {
        return null;
    }
}
