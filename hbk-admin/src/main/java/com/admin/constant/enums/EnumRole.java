package com.admin.constant.enums;

/**
 * @Author: wsp
 * @Date: 2018/12/5 10:51
 */
public enum  EnumRole {

    ROLE_ADMIN("ROLE_ADMIN", "超级管理员"),
    CUSTOMER_MANAGER("CUSTOMER_MANAGER","客户经理"),
    COMMERCIAL_TENANT("COMMERCIAL_TENANT","商户"),
    CUSTOMER("CUSTOMER","客户"),
    USER_ROLE_CUSTOMER_MANAGER("1","客户经理-用户"),
    USER_ROLE_COMMERCIAL_TENANT("0", "商户");

    private String value;
    private String note;

    EnumRole(String value, String note) {
        this.value = value;
        this.note = note;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
