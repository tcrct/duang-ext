package com.duangframework.ext.kit;

import com.duangframework.exception.ServiceException;
import com.duangframework.ext.push.PushFactory;
import com.duangframework.ext.push.PushRequest;
import com.duangframework.ext.push.PushResponse;
import com.duangframework.kit.ToolsKit;
import com.duangframework.vtor.annotation.VtorKit;
import com.duangframework.vtor.core.VtorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laotang on 2019/3/1.
 */
public class PushKit {

    private static PushRequest pushRequest;
    private final static List<String> tagsList = new ArrayList<>();

    private static class PushKitHolder {
        private static final PushKit INSTANCE = new PushKit();
    }
    private PushKit() {
    }
    public static final PushKit duang() {
        clear();
        return PushKitHolder.INSTANCE;
    }
    private static void clear() {
        pushRequest = new PushRequest();
        tagsList.clear();
    }

    /**
     * 推送对象
     * @param request
     * @return
     */
    public PushKit param(PushRequest request) {
        PushKit.pushRequest = request;
        return this;
    }

    /**
     * 标题
     * @param title
     * @return
     */
    public PushKit title(String title) {
        pushRequest.setTitle(title);
        return this;
    }

    /**
     * 内容
     * @param content
     * @return
     */
    public PushKit content(String content) {
        pushRequest.setContent(content);
        return this;
    }

    /**
     * 帐号
     * @param account
     * @return
     */
    public PushKit account(String account) {
        pushRequest.setAccount(account);
        return this;
    }

    /**
     * 是否android手机
     * @param isAndroid
     * @return
     */
    public PushKit isAndroid(boolean isAndroid) {
        pushRequest.setAndroid(isAndroid);
        return this;
    }

    /**
     * 标签
     * @param tagsList
     * @return
     */
    public PushKit tags(List<String> tagsList) {
        PushKit.tagsList.addAll(tagsList);
        return this;
    }

    /**
     *
     * @param idList
     * @return
     */
    public PushKit ids(List<String> idList) {
        PushKit.tagsList.addAll(idList);
        return this;
    }

    private boolean validatorRequestObj() {
        try {
            VtorFactory.validator(pushRequest);
            return true;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public PushResponse pushSingleDevice() {
        if(validatorRequestObj()) {
            return PushFactory.pushSingleDevice(pushRequest);
        }
        return null;
    }
    public PushResponse pushSingleAccount() {
        if(validatorRequestObj()) {
            return PushFactory.pushSingleAccount(pushRequest);
        }
        return null;
    }
    public PushResponse pushAllDevice() {
        return PushFactory.pushAllDevice(pushRequest.isAndroid());
    }
    public PushResponse pushTags() {
        if(ToolsKit.isEmpty(tagsList)) {
            throw new ServiceException("请先调用[tags]方法，设置需要查询的tag集合");
        }
        return PushFactory.pushTags(pushRequest.isAndroid(), tagsList);
    }
    public PushResponse queryPushStatus() {
        if(ToolsKit.isEmpty(tagsList)) {
            throw new ServiceException("请先调用[ids]方法，设置需要查询的ID集合");
        }
        return PushFactory.queryPushStatus(pushRequest.isAndroid(), tagsList);
    }
}
