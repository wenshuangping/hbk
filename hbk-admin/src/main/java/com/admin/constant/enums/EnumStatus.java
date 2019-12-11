package com.admin.constant.enums;

/**
 * C端用户状态
 */
public enum EnumStatus {
    FROST("2", "冻结"),
    NORMAL("1", "正常");

    private String value;
    private String note;

    EnumStatus(String value, String note) {
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
