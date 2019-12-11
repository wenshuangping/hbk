package com.github.gateway.constant.enums;

/**
 * @Author: wsp
 * @Date: 2018/12/7 16:57
 */
public enum EnumPwType {

    /**
     * 明文-1  AES-2
     */
    CLEARTEXT("1", "明文"),
    AES("2", "AES");

    private String value;
    private String note;

    EnumPwType(String value, String note) {
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
