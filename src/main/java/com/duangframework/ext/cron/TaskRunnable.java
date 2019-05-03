package com.duangframework.ext.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 线程执行定时任务类
 * 将实现了ICronTask的类下的每个方法以线程的方式来执行。
 *
 * Created by laotang on 2019/4/27.
 */
public class TaskRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TaskRunnable.class);

    private Method method;
    private Object taskObj;
    private static Object[] NULL_OBJECT = new Object[0];

    public TaskRunnable(CronTask cronTask) {
        this.taskObj = cronTask.getCronTask();
        this.method = cronTask.getMethod();
    }
    @Override
    public void run() {
        try {
            method.invoke(taskObj, NULL_OBJECT);
        } catch (Exception e) {
            logger.warn("TaskRunnable run["+method.getName()+"] is fail: "+ e.getMessage(), e);
        }
    }
}
