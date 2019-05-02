package com.duangframework.ext.kit;

import com.duangframework.ext.push.IPush;
import com.duangframework.ext.push.PushMessage;
import com.duangframework.ext.tencent.xinge.XingeUtils;
import com.duangframework.kit.ObjectKit;
import com.duangframework.kit.PropKit;
import com.duangframework.kit.ToolsKit;

import java.security.PublicKey;

/**
 * Created by laotang on 2019/3/1.
 */
public class PushKit {

    private IPush client;
    private PushMessage message;

    private static class PushKitHolder {
        private static final PushKit INSTANCE = new PushKit();
    }
    private PushKit() {
        if(null == client) {
            String pushClass = PropKit.get("push.client.class", "");
            if (ToolsKit.isEmpty(pushClass)) {
                pushClass = XingeUtils.class.getName();
            }
            try {
                client = (IPush) ObjectKit.newInstance(pushClass);
                client.getClient();
            } catch (Exception e) {

            }
        }
    }
    public static final PushKit duang() {
        clear();
        return PushKitHolder.INSTANCE;
    }
    private static void clear() {
        message = new PushMessage();
    }


    public PushKit client(IPush client) {
        this.client = client;
        return this;
    }

    /**
     * 推送对象
     * @param message
     * @return
     */
    public PushKit param(PushMessage message) {
        this.message = message;
        return this;
    }

    /**
     * 标题
     * @param title
     * @return
     */
    public PushKit title(String title) {
        message.setTitle(title);
        return this;
    }

    /**
     * 内容
     * @param content
     * @return
     */
    public PushKit content(String content) {
        message.setContent(content);
        return this;
    }

    public PushKit account(String account) {
        return this;
    }

    public boolean push() {
        client.pushSingleDevice(message);
    }


}
