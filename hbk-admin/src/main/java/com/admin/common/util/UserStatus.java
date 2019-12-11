package com.admin.common.util;

public enum UserStatus {
    ENABLE("0", "启用"),
    DISABLE("2", "停用");

    private String value;
    private String note;

    UserStatus(String value, String note) {
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
