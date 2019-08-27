package com.duangframework.ext.getui;

import com.duangframework.ext.ConstEnum;
import com.duangframework.ext.dto.sms.SmsLinkDto;
import com.duangframework.ext.dto.sms.SmsMessage;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import com.duangframework.kit.ToolsKit;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.sms.SmsInfo;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  个推
 *  demo: http://docs.getui.com/download.html
 * @author laotang
 */
public class GetUiPushUtils {

    private static final String RESULT_KEY_FIELD = "result";
    private static final String STATUS_KEY_FIELD = "status";
    private static final String OK_FIELD = "ok";
    private static Integer RET_SUCCESS_CODE = 0;
    private static Integer RET_ERROR_CODE = 1;
    private static String RET_SUCCESS = "success";
    private static String RET_ERROR = "error";

    public static PushResponse createPushResponse(IPushResult pushResult) {
        PushResponse pushResponse = null;
        System.out.println("#############pushResult: " + ToolsKit.toJsonString(pushResult));
        Map<String,Object> resMap = pushResult.getResponse();
        String result = resMap.get(RESULT_KEY_FIELD).toString();
        if(OK_FIELD.equalsIgnoreCase(result)) {
            pushResponse = new PushResponse(RET_SUCCESS_CODE, result +"_"+ resMap.get(STATUS_KEY_FIELD).toString(), "");
        } else {
            pushResponse = new PushResponse(RET_ERROR_CODE, RET_ERROR, result);
        }
        return pushResponse;
    }


    private static Style0 createStyle0(String title, String content) {
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
//        style.setChannel("通知渠道id");
//        style.setChannelName("通知渠道名称");
        // 设置通知渠道重要性
        style.setChannelLevel(3);
        return style;
    }

    private static LinkTemplate getLinkTemplate(String title, String content, String targetUrl, SmsMessage smsMessage) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(ConstEnum.GETUI.APP_ID.getValue());
        template.setAppkey(ConstEnum.GETUI.MASTER_SECRET.getValue());
        template.setStyle(createStyle0(title, content));
        // 设置打开的网址地址
        template.setUrl(targetUrl);
        if(null != smsMessage) {
            //短信补量相关参数信息
            template.setSmsInfo(createSmsInfo(smsMessage));
        }
        return template;
    }

    /**
     *推送给单一个用户
     * @param pushRequest
     * @return
     */
    public static GetUiPushRequestDto pushMessageToSingle(PushRequest pushRequest) {
        SingleMessage singleMessage = new SingleMessage();
        singleMessage.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        singleMessage.setOfflineExpireTime(24 * 3600 * 1000);
        if(pushRequest.isAndroid()) {
            singleMessage.setData(getNotificationTemplate(pushRequest.getTitle(), pushRequest.getContent()));
        } else {
            singleMessage.setData(getTransmissionTemplate(pushRequest.getTitle(), pushRequest.getContent()));
        }
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        singleMessage.setPushNetWorkType(0);
        singleMessage.setPriority(1);
        Target target = new Target();
        target.setAppId(ConstEnum.GETUI.APP_ID.getValue());
        target.setClientId(pushRequest.getAccount());
        List<Target> targets = new ArrayList<Target>(){{
            this.add(target);
        }};
        return new GetUiPushRequestDto(singleMessage, targets);
    }

    /**
     * 透传消息模板类
     * @param title 标题
     * @param content   内容
     * @return
     */
    private static NotificationTemplate getNotificationTemplate(String title, String content) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(ConstEnum.GETUI.APP_ID.getValue());
        template.setAppkey(ConstEnum.GETUI.ACCESS_KEY_SECRET.getValue());
        template.setStyle(createStyle0(title, content));
        //ios消息推送
        template.setAPNInfo(getAPNPayload(title, content));
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(content);
        return template;
    }

    private static TransmissionTemplate getTransmissionTemplate(String title,String content) {
        TransmissionTemplate template = new TransmissionTemplate();
        // 设置APPID与APPKEY
        template.setAppId(ConstEnum.GETUI.APP_ID.getValue());
        template.setAppkey(ConstEnum.GETUI.ACCESS_KEY_SECRET.getValue());
        //ios消息推送
        template.setAPNInfo(getAPNPayload(title, content));
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(content);
        return template;
    }

    /**
     * 根据ID集合或别名集合批量推送
     * @param clientIdList  ID集合或别名集合
     * @param title 标题
     * @param content 内容
     * @return
     */
    public static GetUiPushRequestDto pushMessageToList(List<String> clientIdList, String title, String content) {
        ListMessage message = new ListMessage();
        message.setData(getNotificationTemplate(title, content));
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
        List<Target> targets = new ArrayList();
        for(String clientId : clientIdList) {
            Target target = new Target();
            target.setAppId(ConstEnum.GETUI.APP_ID.getValue());
            // 如果是别名则用这个方法
//            target.setAlias(clientId);
            target.setClientId(clientId);
            targets.add(target);
        }
        return new GetUiPushRequestDto(message, targets);
    }

    /**
     * 推送给所有
     * @param pushRequest   推送对象
     * @return
     */
    public static GetUiPushRequestDto pushMessageToApp(PushRequest pushRequest) {
        NotificationTemplate template = getNotificationTemplate(pushRequest.getTitle(), pushRequest.getContent());
        AppMessage appMessage = new AppMessage();
        appMessage.setData(template);
        appMessage.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        appMessage.setOfflineExpireTime(24 * 1000 * 3600);
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions();
        List<String> appIdList = new ArrayList<>();
        appIdList.add(ConstEnum.GETUI.APP_ID.getValue());
        appMessage.setAppIdList(appIdList);
        /*
        //手机类型
        List<String> phoneTypeList = new ArrayList<>();
        //省份
        List<String> provinceList = new ArrayList<>();
        //自定义tag
        List<String> tagList = new ArrayList<>();
        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        cdt.addCondition(AppConditions.REGION, provinceList);
        cdt.addCondition(AppConditions.TAG,tagList);
        */
        appMessage.setConditions(cdt);
        return new GetUiPushRequestDto(appMessage);
    }


    public static SmsInfo createSmsInfo(SmsMessage smsMessage) {
        SmsInfo smsinfo = new SmsInfo();
        //短信模板ID 需要在个推报备开通 才可使用
        smsinfo.setSmsTemplateId(smsMessage.getTemplateCode());
        //模板中占位符的内容k.v 结构
        //注意当使用AppLink时，smsContent不能传值url
        smsinfo.setSmsContent(smsMessage.getTemplateParam());
        SmsLinkDto smsLinkDto = smsMessage.getSmsLinkDto();
        if(null != smsLinkDto) {
            //多久后进行离线补发的时间
            smsinfo.setOfflineSendtime(smsLinkDto.getOfflineSendtime());
            //推送的短信模板中是否选用APPLink进行推送。
            smsinfo.setApplink(true);
            //推送的短信模板中的APPLink链接地址。
            smsinfo.setUrl(smsLinkDto.getUrl());
            //推送的短信模板中的APPLink自定义字段。
            smsinfo.setPayload(smsLinkDto.getPayload());
        }
        return smsinfo;
    }

    /**
     *
     * @param pushRequest
     * @return
     */
    public static Map<String, String> bindCidPnMap(PushRequest pushRequest) {
        SmsMessage smsMessage = pushRequest.getSmsMessage();
        if(ToolsKit.isEmpty(smsMessage)) {
            return null;
        }
        List<String> phoneList = smsMessage.getPhones();
        String pnmd5 = "";
        for(String phone : phoneList) {
            pnmd5 = MD5Util.getMD5Format(phone);
        }
        if(ToolsKit.isNotEmpty(pnmd5)) {
            Map<String, String> cidAndPn = new HashMap<>(phoneList.size());
            cidAndPn.put(pushRequest.getAccount(), pnmd5);
            return cidAndPn;
        }
        return null;
    }


    private static APNPayload getAPNPayload(String title, String content) {
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        //ios 12.0 以上可以使用 Dictionary 类型的 sound
        payload.setSound("default");
//        payload.setCategory("$由客户端定义");
//        payload.addCustomMsg("由客户自定义消息key", "由客户自定义消息value");

        //简单模式APNPayload.SimpleMsg
//        payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
        payload.setAlertMsg(getDictionaryAlertMsg(title, content));  //字典模式使用APNPayload.DictionaryAlertMsg

        //设置语音播报类型，int类型，0.不可用 1.播放body 2.播放自定义文本
//        payload.setVoicePlayType(2);
        //设置语音播报内容，String类型，非必须参数，用户自定义播放内容，仅在voicePlayMessage=2时生效
        //注：当"定义类型"=2, "定义内容"为空时则忽略不播放
//        payload.setVoicePlayMessage("定义内容");

        // 添加多媒体资源
//        payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.pic)
//                .setResUrl("资源文件地址")
//                .setOnlyWifi(true));

        return payload;
    }


    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String content) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(content);
//        alertMsg.setActionLocKey("显示关闭和查看两个按钮的消息");
//        alertMsg.setLocKey("loc-key1");
//        alertMsg.addLocArg("loc-ary1");
//        alertMsg.setLaunchImage("调用已经在应用程序中绑定的图形文件名");
        // iOS8.2以上版本支持
        alertMsg.setTitle(title);
//        alertMsg.setTitleLocKey("自定义通知标题");
//        alertMsg.addTitleLocArg("自定义通知标题组");
        return alertMsg;
    }
}
