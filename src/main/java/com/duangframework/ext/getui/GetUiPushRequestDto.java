package com.duangframework.ext.getui;

import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;

import java.util.List;

/**
 *
 * @author laotang
 */
public class GetUiPushRequestDto implements java.io.Serializable {

    private SingleMessage singleMessage;
    private ListMessage listMessage;
    private AppMessage appMessage;
    private List<Target> targets;


    public GetUiPushRequestDto() {
    }

    public GetUiPushRequestDto(AppMessage appMessage) {
        this.appMessage = appMessage;
    }

    public GetUiPushRequestDto(ListMessage listMessage, List<Target> targets) {
        this.listMessage = listMessage;
        this.targets = targets;
    }

    public GetUiPushRequestDto(SingleMessage singleMessage, List<Target> targets) {
        this.singleMessage = singleMessage;
        this.targets = targets;
    }

    public SingleMessage getSingleMessage() {
        return singleMessage;
    }

    public void setSingleMessage(SingleMessage singleMessage) {
        this.singleMessage = singleMessage;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public ListMessage getListMessage() {
        return listMessage;
    }

    public void setListMessage(ListMessage listMessage) {
        this.listMessage = listMessage;
    }

    public AppMessage getAppMessage() {
        return appMessage;
    }

    public void setAppMessage(AppMessage appMessage) {
        this.appMessage = appMessage;
    }
}
