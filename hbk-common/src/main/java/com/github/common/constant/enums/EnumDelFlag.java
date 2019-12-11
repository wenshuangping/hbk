package com.github.common.constant.enums;

public enum EnumDelFlag {
    UNDELETE("0", "未删除"),
    DELETE("1", "删除");

    private String value;
    private String note;

    EnumDelFlag(String value, String note) {
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
