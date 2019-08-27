package com.duangframework.ext;

//import com.duangframework.encrypt.core.EncryptUtils;

import com.duangframework.ext.dto.sms.SmsMessage;
import com.duangframework.ext.dto.sms.SmsResult;
import com.duangframework.ext.kit.AliyunKit;
import com.duangframework.ext.kit.ExcelKit;
import com.duangframework.ext.kit.PushKit;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import com.duangframework.kit.ToolsKit;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        push();
    }

    private static void sms() {
//        SmsMessage messageDto = new SmsMessage();
//        messageDto.setPhone("13431913636");
//        messageDto.setTemplateCode("SMS_137905143");
//        messageDto.setSmsParam("code", "123456");
//        SmsResult resultDto = AliyunKit.sms().send(messageDto);
//        System.out.println(resultDto.getBizId()+"                                      "+ToolsKit.toJsonString(resultDto));


        SmsMessage messageDto = new SmsMessage();
        messageDto.setTemplateCode("SMS_172880111");
//        messageDto.setTemplateParam(new HashMap<String,String>(){{
//            this.put("name","laotang");
//            this.put("title","");
//        }});
        List<String> phoneList = new ArrayList<String>(){{
            this.add("13268033883");
            this.add("13112361638");
            this.add("13431913636");
        }};
        messageDto.setPhones(phoneList);
        messageDto.setSmsParam("name","laotang");
        messageDto.setSmsParam("title","《关于收购碧桂园集团的决定》");
        SmsResult resultDto =AliyunKit.sms().send(messageDto);
        System.out.println(resultDto.getBizId()+"                                      "+ToolsKit.toJsonString(resultDto));
    }

    private static void push() {
//        String account="927bf2b04a6a6628bb63e60282669b53"; //admin
        String account= "6eafdfe256a53c3b6dba144793be49b9";
//        String account = "c192bfb82b34226acef5bf0fea463542";    // 1234
//        PushResponse pushResponse = PushKit.duang().account("1017").title("这是一封迟来的告白").content("一段情要埋藏多少年！ABCDabcd!@#123").pushSingleAccount();
        PushResponse pushResponse = PushKit.duang().account(account)
                .isAndroid(false)
                .title("这是一封迟来的告白")
                .content("一段情要埋藏多少年\n" +
                        "一封信要迟来多少天\n" +
                        "两颗心要承受多少痛苦的煎熬\n" +
                        "才能够彼此完全明了\n" +
                        "你应该会明白我的爱\n" +
                        "虽然我从未向你坦白\n" +
                        "多年以来默默对你深切的关怀！\n"+"ABCDabcd!@#123")
//                .pushAllDevice();
                .pushSingleAccount();
        System.out.println(ToolsKit.toJsonString(pushResponse));
    }

    private static void excel() {
        String path = "C:\\workspace\\IdeaProjects\\easyexcel\\src\\test\\resources\\批量添加用户模板.xlsx";
        List<BatchImportUserDto> batchImportUserDtoList = ExcelKit.duang().sheet(1).path(path).bean(BatchImportUserDto.class).read2Bean();
        for(BatchImportUserDto dto : batchImportUserDtoList) {
            System.out.println(ToolsKit.toJsonString(dto));
        }

        List<Object> dataList = ExcelKit.duang().sheet(1).path(path).bean(BatchImportUserDto.class).read2Bean();
        for(Object object : dataList) {
            if(ArrayList.class.equals(object)) {
                List<Object> arrayLists = (ArrayList)object;
                for(int i=0; i<arrayLists.size(); i++) {
                    Object value = arrayLists.get(i);
                }
            }
            System.out.println(object.getClass());
        }
    }
}
