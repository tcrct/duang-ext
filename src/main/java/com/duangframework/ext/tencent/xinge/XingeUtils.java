package com.duangframework.ext.tencent.xinge;

import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import com.duangframework.kit.ToolsKit;
import com.tencent.xinge.*;
import org.json.JSONObject;

/**
 * @author laotang
 * @date 2019/5/3
 */
public class XingeUtils {

    /** 元素为 TimeInterval 类型，表示允许推送的时间段，选填*/
    private final static TimeInterval TIME_INTERVAL = new TimeInterval(0, 0, 23, 59);

    /**
     * 将推送请求转换为信鸽的Message对象
     * @param pushRequest
     * @return
     */
    public static Message convert2Message(PushRequest pushRequest) {
        Message message = new Message();
        message.setType(Message.TYPE_NOTIFICATION);
//        Style style = new Style(1);
        Style style = new Style(3, 1, 0, 1, 0);
        ClickAction action = new ClickAction();
        action.setActionType(ClickAction.TYPE_URL);
        action.setUrl("http://xg.qq.com");
        message.setStyle(style);
        message.setAction(action);
        message.addAcceptTime(TIME_INTERVAL);
        if(ToolsKit.isNotEmpty(pushRequest)) {
            message.setTitle(pushRequest.getTitle());
            message.setContent(pushRequest.getContent());
        }
//        Map<String, Object> custom = new HashMap<String, Object>();
//        custom.put("key1", "value1");
//        custom.put("key2", 2);
//        message.setCustom(custom);
        return message;
    }

    /**
     * 将推送请求转换为信鸽的Message对象
     * @param pushRequest
     * @return
     */
    public static MessageIOS convert2MessageIos(PushRequest pushRequest) {
        MessageIOS message = new MessageIOS();
        message.addAcceptTime(TIME_INTERVAL);
        if(ToolsKit.isNotEmpty(pushRequest)) {
            message.setExpireTime(pushRequest.getExpireTime());
            message.setAlert(pushRequest.getTitle());
        }
        message.setBadge(1);
        message.setSound("beep.wav");
        message.setType(MessageIOS.TYPE_APNS_NOTIFICATION);
        message.setCategory("INVITE_CATEGORY");
//        Map<String, Object> custom = new HashMap<String, Object>();
//        custom.put("key1", "value1");
//        custom.put("key2", 2);
//        message.setCustom(custom);
        return message;
    }

    /**
     * 将信鸽返回的信息转换为PushResponse对象
     * @param jsonObject
     * @return
     */
    public static PushResponse convert2Response(JSONObject jsonObject) {
        if(ToolsKit.isEmpty(jsonObject)) {
            return null;
        }
        PushResponse pushResponse = new PushResponse();
        return pushResponse.getXingeResult(jsonObject);
    }

}
