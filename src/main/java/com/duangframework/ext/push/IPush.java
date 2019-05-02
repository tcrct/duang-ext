package com.duangframework.ext.push;

import com.alibaba.fastjson.JSONObject;
import com.duangframework.ext.IClient;

/**
 * Created by laotang on 2019/5/1.
 */
public interface IPush<T> extends IClient<T> {

    /**
     * 推送消息到单个设备
     */
    JSONObject pushSingleDevice(PushMessage message);
}
