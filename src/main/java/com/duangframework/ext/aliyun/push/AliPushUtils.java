package com.duangframework.ext.aliyun.push;

import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.utils.ParameterHelper;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.kit.ToolsKit;
import com.duangframework.server.common.BootStrap;

import java.util.Date;

/**
 * @author laotang
 */
public class AliPushUtils {

    public static Long APP_KEY = 27760428L;
    public static String APP_SECRET = "2f8ffe5a909352d6a1f4c42d492d119f ";

    public static com.aliyuncs.push.model.v20160801.PushRequest createAliyunPushRequest(String target, PushRequest request) {
        com.aliyuncs.push.model.v20160801.PushRequest pushRequest = new com.aliyuncs.push.model.v20160801.PushRequest();
        if(ToolsKit.isNotEmpty(request)) {
            // 消息的标题
            pushRequest.setTitle(request.getTitle());
            // 消息的内容
            pushRequest.setBody(request.getContent());
            //推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送  TAG:按标签推送; ALL: 广播推送
            pushRequest.setTarget(target);
            //根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
            pushRequest.setTargetValue(request.getAccount());
        } else {
            //推送目标: device:推送给设备; account:推送给指定帐号,tag:推送给自定义标签; all: 推送给全部
            pushRequest.setTarget(target);
            //根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
            pushRequest.setTargetValue("ALL");
        }
        //安全性比较高的内容建议使用HTTPS
        pushRequest.setProtocol(ProtocolType.HTTPS);
        //内容较大的请求，使用POST请求
        pushRequest.setMethod(MethodType.POST);
        // 推送目标
        pushRequest.setAppKey(APP_KEY);
        // 消息类型 MESSAGE NOTICE
        pushRequest.setPushType("NOTICE");
        // 设备类型 ANDROID iOS ALL.
        pushRequest.setDeviceType("ANDROID");
        // 30秒之间的时间点, 也可以设置成你指定固定时间
        Date pushDate = new Date(System.currentTimeMillis());
        String pushTime = ParameterHelper.getISO8601Time(pushDate);
        // 延后推送。可选，如果不设置表示立即推送
        pushRequest.setPushTime(pushTime);
        // 12小时后消息失效, 不会再发送
        String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000));
        pushRequest.setExpireTime(expireTime);
        // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
        pushRequest.setStoreOffline(true);
        //通知的提醒方式 "VIBRATE" : 震动 "SOUND" : 声音 "BOTH" : 声音和震动 NONE : 静音

        if(request.isAndroid()) {
            createAndroidRequest(pushRequest);
        } else {
            createIosRequest(pushRequest);
        }
        return pushRequest;
    }

    // 推送配置: Android
    private static void createAndroidRequest(com.aliyuncs.push.model.v20160801.PushRequest pushRequest) {
        pushRequest.setAndroidNotifyType("NONE");
        //通知栏自定义样式0-100
        pushRequest.setAndroidNotificationBarType(1);
        //通知栏自定义样式0-100
        pushRequest.setAndroidNotificationBarPriority(1);
        // Android通知音乐
        pushRequest.setAndroidMusic("default");
        /*
        //点击通知后动作 "APPLICATION" : 打开应用 "ACTIVITY" : 打开AndroidActivity "URL" : 打开URL "NONE" : 无跳转
        pushRequest.setAndroidOpenType("URL");
        //Android收到推送后打开对应的url,仅当AndroidOpenType="URL"有效
        pushRequest.setAndroidOpenUrl("http://www.aliyun.com");
        // 设定通知打开的activity，仅当AndroidOpenType="Activity"有效
        pushRequest.setAndroidActivity("com.alibaba.push2.demo.XiaoMiPushActivity");
        //设置该参数后启动小米托管弹窗功能, 此处指定通知点击后跳转的Activity（托管弹窗的前提条件：1. 集成小米辅助通道；2. StoreOffline参数设为true）
        pushRequest.setAndroidXiaoMiActivity("com.ali.demo.MiActivity");
        pushRequest.setAndroidXiaoMiNotifyTitle("Mi title");
        pushRequest.setAndroidXiaoMiNotifyBody("MiActivity Body");
        //设定通知的扩展属性。(注意 : 该参数要以 json map 的格式传入,否则会解析出错)
        pushRequest.setAndroidExtParameters("{\"k1\":\"android\",\"k2\":\"v2\"}");
        */
    }
    // 推送配置: iOS
    private static void createIosRequest(com.aliyuncs.push.model.v20160801.PushRequest pushRequest) {
        // iOS应用图标右上角角标
        pushRequest.setIOSBadge(5);
        //开启静默通知
        pushRequest.setIOSSilentNotification(false);
        // iOS通知声音
        pushRequest.setIOSMusic("default");
        //iOS10通知副标题的内容
        pushRequest.setIOSSubtitle("iOS10 subtitle");
        //指定iOS10通知Category
        pushRequest.setIOSNotificationCategory("iOS10 Notification Category");
        //是否允许扩展iOS通知内容
        pushRequest.setIOSMutableContent(true);
        //iOS的通知是通过APNs中心来发送的，需要填写对应的环境信息。"DEV" : 表示开发环境 "PRODUCT" : 表示生产环境
        String env = BootStrap.getInstants().isDevModel() ? "DEV" : "PRODUCT";
        pushRequest.setIOSApnsEnv(env);
        // 消息推送时设备不在线（既与移动推送的服务端的长连接通道不通），则这条推送会做为通知，通过苹果的APNs通道送达一次。注意：离线消息转通知仅适用于生产环境
        pushRequest.setIOSRemind(true);
        //iOS消息转通知时使用的iOS通知内容，仅当iOSApnsEnv=PRODUCT && iOSRemind为true时有效
        pushRequest.setIOSRemindBody("iOSRemindBody");
        //通知的扩展属性(注意 : 该参数要以json map的格式传入,否则会解析出错)
//        pushRequest.setIOSExtParameters("{\"_ENV_\":\"DEV\",\"k2\":\"v2\"}");
    }
}
