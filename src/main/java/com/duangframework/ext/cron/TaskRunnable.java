package com.duangframework.ext.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
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
