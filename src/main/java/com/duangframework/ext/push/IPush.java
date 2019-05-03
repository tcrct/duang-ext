package com.duangframework.ext.push;

import com.duangframework.ext.IClient;

/**
 * @author laotang
 * @date 2019/5/3
 * @param <T>
 */
public interface IPush<T> extends IClient<T> {

    /**
     * 取推送算法
     * @param isAndroid     是否安卓机
     * @return
     */
    IPushAlgorithm getPushAlgorithm(boolean isAndroid);

}
