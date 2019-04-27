package com.duangframework.ext.cron;

import java.lang.reflect.Method;

/**
 *  定时任务实体类
 * Created by laotang on 2019/4/27.
 */
public class CronTask implements java.io.Serializable {
    // 要执行定时任务的类
    private ICronTask cronTask;
    // 执行定时任务的方法，不带参数
    private Method method;
    // 定时任务的表达式
    private String taskExp;
    // 定时执行的任务线程类
    private TaskRunnable taskRunnable;

    public CronTask() {
    }

    public CronTask(ICronTask cronTask, Method method, String taskExp) {
        this.cronTask = cronTask;
        this.method = method;
        this.taskExp = taskExp;
    }

    public ICronTask getCronTask() {
        return cronTask;
    }

    public void setCronTask(ICronTask cronTask) {
        this.cronTask = cronTask;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getTaskExp() {
        return taskExp;
    }

    public void setTaskExp(String taskExp) {
        this.taskExp = taskExp;
    }

    public TaskRunnable getTaskRunnable() {
        return taskRunnable;
    }

    public void setTaskRunnable(TaskRunnable taskRunnable) {
        this.taskRunnable = taskRunnable;
    }
}
