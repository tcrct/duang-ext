package com.duangframework.ext.getui;

import com.duangframework.ext.ConstEnum;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetUiPushUtils {

    private static final String RESULT_KEY_FIELD = "result";
    private static final String OK_FIELD = "ok";
    private static Integer RET_SUCCESS_CODE = 0;
    private static Integer RET_ERROR_CODE = 1;
    private static String RET_SUCCESS = "success";
    private static String RET_ERROR = "error";

    public static PushResponse createPushResponse(IPushResult pushResult) {
        PushResponse pushResponse = null;
        Map<String,Object> resMap = pushResult.getResponse();
        String result = resMap.get(RESULT_KEY_FIELD).toString();
        if(OK_FIELD.equalsIgnoreCase(result)) {
            pushResponse = new PushResponse(RET_SUCCESS_CODE, RET_SUCCESS, "");
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
        return style;
    }

    private static LinkTemplate getLinkTemplate(String title, String content, String targetUrl) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(ConstEnum.GETUI.ACCESS_KEY_ID.getValue());
        template.setAppkey(ConstEnum.GETUI.ACCESS_KEY_SECRET.getValue());
        template.setStyle(createStyle0(title, content));
        // 设置打开的网址地址
        template.setUrl(targetUrl);
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
        singleMessage.setData(getLinkTemplate(pushRequest.getTitle(), pushRequest.getContent(), pushRequest.getTargetUrl()));
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        singleMessage.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(ConstEnum.GETUI.ACCESS_KEY_ID.getValue());
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
        template.setAppId(ConstEnum.GETUI.ACCESS_KEY_ID.getValue());
        template.setAppkey(ConstEnum.GETUI.ACCESS_KEY_SECRET.getValue());
        template.setStyle(createStyle0(title, content));
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
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
            target.setAppId(ConstEnum.GETUI.ACCESS_KEY_ID.getValue());
            // 如果是别名则用这个方法
//            target.setAlias(clientId);
            target.setClientId(clientId);
            targets.add(target);
        }
        return new GetUiPushRequestDto(message, targets);
    }

    /**
     * 推送给所有
     * @param title
     * @param content
     * @return
     */
    public static GetUiPushRequestDto pushMessageToApp(String title, String content) {
        LinkTemplate template = getLinkTemplate(title, content, "");
        AppMessage appMessage = new AppMessage();
        appMessage.setData(template);
        appMessage.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        appMessage.setOfflineExpireTime(24 * 1000 * 3600);
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions();
        List<String> appIdList = new ArrayList<>();
        appIdList.add(ConstEnum.GETUI.ACCESS_KEY_ID.getValue());
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

}
