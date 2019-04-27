package com.duangframework.ext.cron.annotation;

import java.lang.annotation.*;

/**
 *  cron注解
 * cron 表达式由五部分组成：分 时 天 月 周
 * 分 ：从 0 到 59
 * 时 ：从 0 到 23
 * 天 ：从 1 到 31，字母 L 可以表示月的最后一天
 * 月 ：从 1 到 12，可以别名：jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov" and "dec"
 * 周 ：从 0 到 6，0 表示周日，6 表示周六，可以使用别名： "sun", "mon", "tue", "wed", "thu", "fri" and "sat"
 *
 * 数字 n：表示一个具体的时间点，例如 5 * * * * 表示 5 分这个时间点时执行
 * 逗号 , ：表示指定多个数值，例如 3,5 * * * * 表示 3 和 5 分这两个时间点执行
 * 减号 -：表示范围，例如 1-3 * * * * 表示 1 分、2 分再到 3 分这三个时间点执行
 * 星号 *：表示每一个时间点，例如 * * * * * 表示每分钟执行
 * 除号 /：表示指定一个值的增加幅度。例如 n/m表示从 n 开始，每次增加 m 的时间点执行
 * Created by laotang on 2019/4/27.
 */
@Target(ElementType.METHOD )
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cron {
    // 定时器表达式
    String value() default "";
    // 是否开启，默认为开启
    boolean isEnable() default true;
}
