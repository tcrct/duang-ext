package com.duangframework.ext.kit;

import com.alibaba.excel.metadata.BaseRowModel;
import com.duangframework.ext.excel.ReadExcel;
import com.duangframework.kit.ToolsKit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelKit {

    private static class ExcelKitHolder {
        private static final ExcelKit INSTANCE = new ExcelKit();
    }
    private ExcelKit() {
    }
    public static final ExcelKit duang() {
        clear();
        return ExcelKitHolder.INSTANCE;
    }
    private static void clear() {
        path = "";
        sheetNo = 1;
    }

    private static String path;
    private static int sheetNo;
    private static Class modelClass;
    public ExcelKit path(String path) {
        ExcelKit.path = path;
        return this;
    }

    public ExcelKit sheet(int sheetNo) {
        ExcelKit.sheetNo = sheetNo;
        return this;
    }

    public ExcelKit bean(Class<? extends BaseRowModel> modelClass) {
        ExcelKit.modelClass = modelClass;
        return this;
    }

    public List<Object> read() {
        List<Object> dataList = ReadExcel.simpleReadListStringV2007(new File(ExcelKit.path), sheetNo);
        return ToolsKit.isEmpty(dataList) ? new ArrayList<>(1) : dataList;
    }

    public <T> List<T> read2Bean() {
        List<T> dataList = ReadExcel.simpleReadListStringV2007(new File(ExcelKit.path), sheetNo, modelClass);
        return ToolsKit.isEmpty(dataList) ? new ArrayList<>(1) : dataList;
    }

}
