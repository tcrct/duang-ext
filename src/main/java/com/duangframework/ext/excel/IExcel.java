package com.duangframework.ext.excel;

import java.util.List;

/**
 * 定义导入Excel文件接口
 *
 * @author laotang
 */
public interface IExcel<T> {

    /**
     *创建标签页名称
     * @param sheelName
     * @param filePath
     */
    void createExcelFile(String sheelName, String filePath) throws Exception;

    /**
     * 下载
     * @throws Exception
     */
    void download() throws Exception;


}
