package com.duangframework.ext.getui;

import com.duangframework.ext.ConstEnum;
import com.duangframework.ext.dto.sms.SmsMessage;
import com.duangframework.ext.push.IPushAlgorithm;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import com.duangframework.kit.ToolsKit;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.http.IGtPush;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 个推推送(Android/iOS)封装
 * @author laotang
 */
public class GetUiPushAlgorithm implements IPushAlgorithm {

    private final static Logger logger = LoggerFactory.getLogger(GetUiPushAlgorithm.class);

    private static GetUiPushAlgorithm INSTANCE = new GetUiPushAlgorithm();
    private static Integer RET_ERROR_CODE = 1;
    private static String RET_ERROR = "error";

    public static GetUiPushAlgorithm getInstance() {
        return INSTANCE;
    }

    private IGtPush getClient(){
        try {
            return GetUiPushClient.getInstance().getClient();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    private PushResponse push(PushRequest pushRequest) {
        try{
            GetUiPushRequestDto requestDto = GetUiPushUtils.pushMessageToSingle(pushRequest);
            Map<String,String> smsMap = GetUiPushUtils.bindCidPnMap(pushRequest);
            if(ToolsKit.isNotEmpty(smsMap)) {
                getClient().bindCidPn(ConstEnum.GETUI.APP_ID.getValue(), smsMap);
            }
            IPushResult pushResult = getClient().pushMessageToSingle(requestDto.getSingleMessage(), requestDto.getTargets().get(0));
            return GetUiPushUtils.createPushResponse(pushResult);
        } catch (Exception e) {
            return new PushResponse(RET_ERROR_CODE, RET_ERROR, e.getMessage());
        }
    }

    @Override
    public PushResponse pushSingleDevice(PushRequest pushRequest) {
        try {
            return null;
        } catch (Exception e) {
            return new PushResponse(RET_ERROR_CODE, RET_ERROR, e.getMessage());
        }
    }


    @Override
    public PushResponse pushSingleAccount(PushRequest pushRequest) {
        try {
            return push(pushRequest);
        } catch (Exception e) {
            return new PushResponse(RET_ERROR_CODE, RET_ERROR, e.getMessage());
        }
    }

    @Override
    public PushResponse pushAllDevice(PushRequest pushRequest) {
        try {
            GetUiPushRequestDto requestDto = GetUiPushUtils.pushMessageToApp(pushRequest);
            System.out.println(requestDto);
            IPushResult pushResult =  getClient().pushMessageToApp(requestDto.getAppMessage());
            return GetUiPushUtils.createPushResponse(pushResult);
        } catch (Exception e) {
            return new PushResponse(RET_ERROR_CODE, RET_ERROR, e.getMessage());
        }
    }

    @Override
    public PushResponse pushTags(List<String> tagsList, PushRequest pushRequest) {
        try {
            GetUiPushRequestDto requestDto = GetUiPushUtils.pushMessageToList(tagsList, pushRequest.getTitle(), pushRequest.getContent() );
            // taskId用于在推送时去查找对应的message
            String taskId = getClient().getContentId(requestDto.getListMessage());
            IPushResult pushResult =  getClient().pushMessageToList(taskId, requestDto.getTargets());
            return GetUiPushUtils.createPushResponse(pushResult);
        } catch (Exception e) {
            return new PushResponse(RET_ERROR_CODE, RET_ERROR, e.getMessage());
        }
    }

    /**
     * 没实现
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
