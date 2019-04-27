package com.duangframework.ext.cron;

import com.duangframework.ext.cron.annotation.Cron;
import com.duangframework.kit.ObjectKit;
import com.duangframework.kit.ToolsKit;
import com.duangframework.mvc.plugin.IPlugin;
import it.sauronsoftware.cron4j.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 定时器插件
 *
 * Created by laotang on 2019/4/27.
 */
public class Cron4jPlugin implements IPlugin {

    public static final Logger logger = LoggerFactory.getLogger(Cron4jPlugin.class);

    public Class<?> cronClass;
    private Scheduler scheduler;
    private ICronTask iCronTask;
    private List<CronTask> cronTaskList;

    public Cron4jPlugin(Class<? extends  ICronTask> cron4jClass) {
        this.cronClass = cron4jClass;
        scheduler = new Scheduler();
        cronTaskList = new ArrayList();
    }

    @Override
    public void start() throws Exception {
        loadTask();
        startTask();
    }

    @Override
    public void stop() throws Exception {
        scheduler.stop();
        cronTaskList.clear();
    }

    private void loadTask() {
        if(ToolsKit.isEmpty(cronClass)) {
            throw new NullPointerException("cron class is null");
        }
        Method[] methods = cronClass.getMethods();
        if(ToolsKit.isEmpty(methods)) {
            throw new NullPointerException("cron class method is null");
        }
        iCronTask = ObjectKit.newInstance(cronClass);
        for(Method method : methods) {
            Cron cron = method.getAnnotation(Cron.class);
            if(ToolsKit.isEmpty(cron)) {
                continue;
            }
            String taskCronExp = cron.value();
            if(!cron.isEnable() && ToolsKit.isEmpty(taskCronExp)) {
                continue;
            }
            CronTask cronTask = new CronTask(iCronTask, method, taskCronExp);
            cronTask.setTaskRunnable(new TaskRunnable(cronTask));
            cronTaskList.add(cronTask);
        }
    }

    private void startTask() {
        if(ToolsKit.isEmpty(cronTaskList)) {
            logger.warn("start task cronTaskList is null, exit...");
            return;
        }
        for(CronTask cronTask : cronTaskList) {
            Runnable runnable = cronTask.getTaskRunnable();
            String cronExp = cronTask.getTaskExp();
            if(ToolsKit.isNotEmpty(runnable) && ToolsKit.isNotEmpty(cronExp)) {
                scheduler.schedule(cronExp, runnable);
                logger.warn("cron exp[" + cronExp + "] exec method: " + cronTask.getCronTask().getClass().getName()+"."+cronTask.getMethod().getName());
            }
        }
        scheduler.setDaemon(iCronTask.isDaemon());
        scheduler.start();
    }
}
