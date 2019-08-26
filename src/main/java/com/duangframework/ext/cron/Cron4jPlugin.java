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
 * 定时任务，目前有两种框架，一个是corn4j,一个是quartz
 *
 * 这里我们分别做下介绍
 *
 * corn4j其本身不支持秒，可以通过修改其源文件重新打包来实现支持，其默认规则如下
 * 分：从0到59
 * 时：从0到23
 * 天：从1到31，字母L可以表示月的最后一天
 * 月：从1到12，可以别名："jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov" and "dec"
 * 周：从 0 到 6，0 表示周日，6 表示周六，可以使用别名： "sun", "mon", "tue", "wed", "thu", "fri" and "sat"
 *
 * 数字 n：表示一个具体的时间点，例如 5 * * * * 表示 5 分这个时间点时执行
 * 逗号 , ：表示指定多个数值，例如 3,5 * * * * 表示 3 和 5 分这两个时间点执行
 * 减号 -：表示范围，例如 1-3 * * * * 表示 1 分、2 分再到 3 分这三个时间点执行
 * 星号 *：表示每一个时间点，例如 * * * * * 表示每分钟执行
 * 除号 /：表示指定一个值的增加幅度。例如 * /5表示每隔5分钟执行一次（序列：0:00, 0:05, 0:10, 0:15 等等）。
 * 再例如3-18/5 * * * * 是指在从3到18分钟值这个范围之中每隔5分钟执行一次（序列：0:03, 0:08, 0:13, 0:18, 1:03, 1:08 等等）。
 *
 * @author laotang
 * @date 2019/4/27.
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
