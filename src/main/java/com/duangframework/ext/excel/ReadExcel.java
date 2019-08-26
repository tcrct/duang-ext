package com.duangframework.ext.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.duangframework.ext.excel.listen.ExcelListener;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 读取Excel
 *
 * @author laotang
 */
public class ReadExcel {

    private static final Logger logger = LoggerFactory.getLogger(ReadExcel.class);

    private static InputStream getExcelFileInputStream(File file) {
        InputStream inputStream = null;
        try {
            inputStream = FileUtils.openInputStream(file);
        } catch (Exception e) {
            logger.warn(e.getMessage() ,e);
        }
        return inputStream;
    }

    public static List<Object> simpleReadListStringV2007(File file, int sheetNo) {
        return simpleReadListStringV2007(file, sheetNo, null);
    }
    /**
     * 07版本excel读数据量少于1千行数据，内部采用回调方法.
     *
     */
    public static List<Object> simpleReadListStringV2007(File file, int sheetNo, Class<? extends BaseRowModel> modelClass) {
        InputStream inputStream = null;
        try {
            inputStream = getExcelFileInputStream(file);
            Sheet sheet = null;
            if(null == modelClass) {
                sheet = new Sheet(sheetNo, 1);
            } else {
                sheet = new Sheet(sheetNo, 1, modelClass);
            }
            return EasyExcelFactory.read(inputStream, sheet);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(null != inputStream) {
                try {inputStream.close();} catch (Exception e) {}
            }
        }
    }

    /**
     * 07版本excel读数据量大于1千行，内部采用回调方法.
     *
     */
    public static void saxReadListStringV2007(File file, int sheetNo, ExcelListener excelListener)  {
        InputStream inputStream = null;
        try {
            inputStream = getExcelFileInputStream(file);
//            ExcelListener excelListener = new ExcelListener();
            EasyExcelFactory.readBySax(inputStream, new Sheet(sheetNo, 1), excelListener);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(null != inputStream) {
                try {inputStream.close();} catch (Exception e) {}
            }
        }
    }


    /**
     * 07版本excel读取sheet
     *
     */
    public static void saxReadSheetsV2007(File file, ExcelListener excelListener) {
        InputStream inputStream = null;
        try {
            inputStream = getExcelFileInputStream(file);
            ExcelReader excelReader = EasyExcelFactory.getReader(inputStream, excelListener);
            List<Sheet> sheets = excelReader.getSheets();
            if(sheets.isEmpty()) {
                throw new RuntimeException("sheets is null");
            }
            for (Sheet sheet:sheets) {
                excelReader.read(sheet);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            if(null != inputStream) {
                try {inputStream.close();} catch (Exception e) {}
            }
        }
    }



    /**
     * 03版本excel读数据量少于1千行数据，内部采用回调方法.
     */
    public static List<Object> simpleReadListStringV2003(File file,int sheetNo) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = getExcelFileInputStream(file);
            return EasyExcelFactory.read(inputStream, new Sheet(sheetNo, 0));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {inputStream.close();} catch (Exception e) {}
        }
    }

    /**
     * 03版本excel读数据量大于1千行数据，内部采用回调方法.
     */
    public static void saxReadListStringV2003(File file, int sheetNo, ExcelListener excelListener) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = getExcelFileInputStream(file);
            EasyExcelFactory.readBySax(inputStream, new Sheet(sheetNo, 0), excelListener);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {inputStream.close();} catch (Exception e) {}
        }
    }


    /**
     * 00版本excel读取sheet
     */
    public static void saxReadSheetsV2003(File file, ExcelListener excelListener) {
        InputStream inputStream = null;
        try {
            inputStream = getExcelFileInputStream(file);
            ExcelReader excelReader = EasyExcelFactory.getReader(inputStream,  excelListener);
            List<Sheet> sheets = excelReader.getSheets();
            if(sheets.isEmpty()) {
                throw new RuntimeException("sheets is null");
            }
            for (Sheet sheet:sheets) {
                excelReader.read(sheet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {inputStream.close();} catch (Exception e) {}
        }
    }
}
