package com.admin.constant.enums;

/**
 * @Author: wsp
 * @Date: 2018/12/4 17:50
 */
public enum EnumArea {

    ROOT("0", "根节点");

    private String value;
    private String note;

    EnumArea(String value, String note) {
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
