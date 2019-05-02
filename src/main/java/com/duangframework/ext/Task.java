package com.duangframework.ext;

import com.duangframework.ext.cron.ICronTask;
import com.duangframework.ext.cron.annotation.Cron;
import com.duangframework.kit.ToolsKit;
import com.duangframework.mvc.http.enums.ConstEnums;

import java.util.Date;

/**
 * Created by laotang on 2019/4/27.
 */
public class Task implements ICronTask{

    @Cron(value = "*/1 * * * *")
    public void sendMail() {
        System.out.println(ToolsKit.formatDate(new Date(), ConstEnums.DEFAULT_DATE_FORMAT_VALUE.getValue()) + " exec task send email...");
    }

    @Cron(value = "*/2 * * * *")
    public void sendSms() {
        System.out.println(ToolsKit.formatDate(new Date(), ConstEnums.DEFAULT_DATE_FORMAT_VALUE.getValue()) + " exec task send sms...");
    }

}
