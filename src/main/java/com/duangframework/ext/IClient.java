package com.duangframework.ext;

/**
 *  所有第三方工具的客户端接口
 */
public interface IClient<T> {

    /**
     * 取客户端
     * @return
     * @throws Exception
     */
    T getClient() throws Exception;

    /**
     * 取客户端是否成功
     * @return
     */
    boolean isSuccess();
}
