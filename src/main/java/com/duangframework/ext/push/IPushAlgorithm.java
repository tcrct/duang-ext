package com.duangframework.ext.push;

import java.util.List;

/**
 * 推送算法接口
 * @author laotang
 * @date 2019/5/1.
 */
public interface IPushAlgorithm {
    /**
     * 推送消息到单个设备
     */
    PushResponse pushSingleDevice(PushRequest pushRequest);
    /**
     * 推送消息给单个账号
     */
    PushResponse pushSingleAccount(PushRequest pushRequest);

    /**
     * 推送消息给单个 app 的所有设备
     * @return
     */
    PushResponse pushAllDevice();

    /**
     *  推送消息给 tags 指定的设备
     */
    PushResponse pushTags(List<String> tagsList);

    /**
     *查询群发消息发送状态
     * @param pushIdList
     */
    PushResponse queryPushStatus(List<String> pushIdList);

    /**
     * 查询应用覆盖的设备数
     * @return
     */
    PushResponse queryDeviceCount();
}
