package com.duangframework.ext.aliyun.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.duangframework.ext.IClient;
import com.duangframework.ext.dto.sms.SmsMessage;
import com.duangframework.ext.dto.sms.SmsResult;
import com.duangframework.kit.ToolsKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SmsUtils implements IClient<IAcsClient> {

    private static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    private static Lock lock = new ReentrantLock();
    private static IAcsClient smsClient;
    private static SmsUtils INSTANCE;

    public static SmsUtils getInstance() {
        try {
            lock.lock();
            if (ToolsKit.isEmpty(INSTANCE)) {
                INSTANCE = new SmsUtils();
            }
        } catch (Exception e) {
            logger.warn("SmsUtils getInstance is fail: " + e.getMessage(), e);
        }finally {
            lock.unlock();
        }
        return INSTANCE;
    }

    // 创建Client实例
    @Override
    public IAcsClient getClient() throws Exception {
        if(ToolsKit.isEmpty(smsClient)) {
            DefaultProfile profile = DefaultProfile.getProfile("default",  ConstEnum.ALIYUN.ACCESS_KEY_ID.getValue(), ConstEnum.ALIYUN.ACCESS_KEY_SECRET.getValue());
             smsClient = new DefaultAcsClient(profile);
        }
        return smsClient;
    }

    @Override
    public boolean isSuccess() {
        return ToolsKit.isNotEmpty(smsClient);
    }


    /**
     * 发送短信
     * @param SmsMessageDto       发送短信内容
     * @return
     */
    public SmsResult send(SmsMessage messageDto) {
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(ConstEnum.ALIYUN.SMS_DOMAIN.getValue());
        request.setVersion(ConstEnum.ALIYUN.SMS_VERSION.getValue());
        List<String> phoneList = messageDto.getPhones();
        if(ToolsKit.isEmpty(phoneList)) {
            throw new NullPointerException("sned sms message is fail: phone is null");
        }
        if(phoneList.size() > 1) {
            request.setAction(ConstEnum.ALIYUN.SMS_SEND_BATCH_SMS_FIELD.getValue());
            request.putQueryParameter(ConstEnum.ALIYUN.SMS_PHONE_NUMBER_FIELD.getValue(), ToolsKit.toJsonString(phoneList));
        } else {
            request.setAction(ConstEnum.ALIYUN.SMS_SENDSMS_FIELD.getValue());
            request.putQueryParameter(ConstEnum.ALIYUN.SMS_PHONE_NUMBER_FIELD.getValue(), phoneList.get(0));
        }
        request.putQueryParameter(ConstEnum.ALIYUN.SMS_SIGN_NAME_FIELD.getValue(), ConstEnum.ALIYUN.SMS_SIGN_NAME.getValue());
        request.putQueryParameter(ConstEnum.ALIYUN.SMS_CODE_FIELD.getValue(), messageDto.getTemplateCode());
        String paramsJson = ToolsKit.toJsonString(messageDto.getTemplateParam());
        request.putQueryParameter(ConstEnum.ALIYUN.SMS_PARAM_FIELD.getValue(), paramsJson);
        try {
            CommonResponse response = getClient().getCommonResponse(request);
            String json = response.getData();
            return ToolsKit.jsonParseObject(json, SmsResult.class);
        } catch (ServerException e) {
            logger.warn(e.getMessage(), e);
        } catch (ClientException e) {
            logger.warn(e.getMessage(),e);
        } catch (Exception e) {
            logger.warn(e.getMessage(),e);
        }
        return null;
    }
}
