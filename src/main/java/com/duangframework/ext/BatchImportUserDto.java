package com.duangframework.ext;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.duangframework.mvc.annotation.Param;

/**
 * 批量导入用户数据Dto
 * @author laotang
 */
public class BatchImportUserDto extends BaseRowModel {

    @ExcelProperty(index = 0)
    @Param(label = "用户帐号")
    private String account;

    @ExcelProperty(index = 1)
    @Param(label = "用户名")
    private String userName;

    @ExcelProperty(index = 2)
    @Param(label = "手机号码")
    private String phone;

    @ExcelProperty(index = 3)
    @Param(label = "性别")
    private String sex;

    @ExcelProperty(index = 4)
    @Param(label = "电子邮箱")
    private String email;

    @ExcelProperty(index = 5)
    @Param(label = "地址")
    private String address;

    @ExcelProperty(index = 6)
    @Param(label = "备注")
    private String remark;

    public BatchImportUserDto() {

    }

    public BatchImportUserDto(String account, String userName, String phone, String sex, String email, String address, String remark) {
        this.account = account;
        this.userName = userName;
        this.phone = phone;
        this.sex = sex;
        this.email = email;
        this.address = address;
        this.remark = remark;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
