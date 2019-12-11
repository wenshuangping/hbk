package com.github.auth.constant.enums;

/**
 * @Author: wsp
 * @Date: 2018/12/7 16:57
 */
public enum EnumUserSource {


    ZL("zl", "职工");


    private String value;
    private String note;

    EnumUserSource(String value, String note) {
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
