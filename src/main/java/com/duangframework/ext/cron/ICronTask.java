package com.duangframework.ext.cron;

/**
 * 定时任务的接口
 * 实现了是否后台运行方法，默认为是
 *
 * Created by laotang on 2019/4/27.
 */
public interface ICronTask {

    default boolean isDaemon() {
        return true;
    }
}
