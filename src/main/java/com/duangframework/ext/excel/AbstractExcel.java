package com.duangframework.ext.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import com.duangframework.kit.ClassKit;
import com.duangframework.kit.ObjectKit;
import com.duangframework.kit.ToolsKit;
import com.duangframework.mvc.annotation.Param;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExcel<T> implements IExcel<T> {

    private Class tClazz;
    private List<T> exportDatas;
    private List<Field> exprotFields;

    private AbstractExcel() {

    }

    /**
     * 导出所有字段
     * @param exportDataList    导出的数据
     */
    public AbstractExcel(List<T> exportDataList){
        exportDatas = exportDataList;
    }

    /**
     * 导出指定字段
     * @param fields    指定导出的字段
     * @param exportDataList    导出的数据
     */
    public AbstractExcel(List<Field> fields, List<T> exportDataList) {
        exprotFields = fields;
        exportDatas = exportDataList;
    }

    /**
     * 导出指定字段
     * @param fields    指定导出的字段
     * @param exportDataList    导出的数据
     */
    public AbstractExcel(Class tClass, List<Field> fields, List<T> exportDataList) {
        this.tClazz = tClass;
        exprotFields = fields;
        exportDatas = exportDataList;
    }

    @Override
    public void download() throws Exception {

    }

    /**
     *
     * @param sheelName
     * @param filePath
     * @param exportDataList
     */
    @Override
    public void createExcelFile(String sheelName, String filePath) throws Exception {
        OutputStream out = new FileOutputStream(filePath);
        ExcelWriter writer = EasyExcelFactory.getWriter(out);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
        Sheet sheet = new Sheet(1, 1);
        sheet.setSheetName(sheelName);

        TableStyle tableStyle = new TableStyle();
        createStyle(tableStyle);
        sheet.setTableStyle(tableStyle);

        List<List<String>> headLists = new ArrayList<>();
        Class<?> tClass = getGenericTypeClass();
        if(ToolsKit.isEmpty(tClass)) {
            tClass = tClazz;
        }
        List<Field> exportFieldList = new ArrayList<>();
        if(ToolsKit.isNotEmpty(exprotFields)) {
            exportFieldList.addAll(exprotFields);
        }
        // 表头
        createHead(headLists, ObjectKit.newInstance(tClass), exportFieldList);
        sheet.setHead(headLists);
        // 表主体
        List<List<Object>> bodyLists = new ArrayList<>();
        createBody(bodyLists, exportDatas, exportFieldList);

        writer.write1(bodyLists, sheet);
        writer.finish();
        out.close();
    }

    /**
     * 创建表格样式
     * @param tableStyle 表格样式
     */
    protected void createStyle(TableStyle tableStyle) {
        Font headFont = new Font();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short)22);
        headFont.setFontName("楷体");
        tableStyle.setTableHeadFont(headFont);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.BLUE);

        Font contentFont = new Font();
        contentFont.setBold(true);
        contentFont.setFontHeightInPoints((short)22);
        contentFont.setFontName("黑体");
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.GREEN);
    }

    /**
     * 创建表格表头
     * @param headList 表头字段
     * @param entityObj 泛型类
     */
    protected void createHead(List<List<String>> headList, T entityObj, List<Field> exportFieldList){
        Field[] fields = null;
        if(ToolsKit.isEmpty(exportFieldList)) {
            fields = ClassKit.getFields(entityObj.getClass());
        } else {
            fields = exportFieldList.toArray(new Field[]{});
        }
        exportFieldList.clear();
        for (Field field : fields) {
            List<String> headCoulumn = new ArrayList<>();
            Param param = field.getAnnotation(Param.class);
            String fieldName = ToolsKit.isEmpty(param) ? field.getName() : param.label();
            headCoulumn.add(fieldName);
            headList.add(headCoulumn);
            exportFieldList.add(field);
        }
    }

    /**
     * 创建表格的主体内容
     * @param bodyList 生成表的数据
     * @param exportDatas 导出的数据
     */
    protected void createBody(List<List<Object>> bodyList, List<T> exportDatas,  List<Field> exportFieldList) {
        for(T entity : exportDatas) {
            List<Object> objectList = new ArrayList<>();
            for (Field field : exportFieldList) {
                objectList.add(ObjectKit.getFieldValue(entity, field));
            }
            bodyList.add(objectList);
        }
    }



    private Class getGenericTypeClass() {
        Class clazz = null;
        Type type = getClass().getGenericSuperclass();
        if( type instanceof ParameterizedType){
            ParameterizedType pType = (ParameterizedType)type;
            Type claz = pType.getActualTypeArguments()[0];
            if( claz instanceof Class ){
                clazz = (Class<T>) claz;
            }
        }
        return clazz;
    }

}
