package com.duangframework.ext.push;

import com.duangframework.kit.ToolsKit;
import org.json.JSONObject;

/**
 * 推送返回消息
 * @author laotang
 * @date 2019/5/3
 */
public class PushResponse implements java.io.Serializable {

    private static final String RET_CODE = "ret_code";
    private static final String RESULT = "result";
    private static final String ERR_MSG = "err_msg";

    private Integer retCode = -1;
    private String result;
    private String errMsg;

    public PushResponse() {
    }

    public PushResponse getXingeResult(JSONObject jsonObject) {
        if(ToolsKit.isNotEmpty(jsonObject)) {
            retCode = jsonObject.getInt(RET_CODE);
            result = jsonObject.getString(RESULT);
            errMsg = jsonObject.getString(ERR_MSG);
        }
        return this;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public String getResult() {
        return result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public String toString() {
        return "PushResponse{" +
                "retCode=" + retCode +
                ", result='" + result + '\'' +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
