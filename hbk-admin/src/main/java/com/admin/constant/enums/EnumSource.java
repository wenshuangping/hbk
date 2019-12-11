package com.admin.constant.enums;

/**
 * C端用户来源
 */
public enum EnumSource {
    REGISTER("0", "注册"),
    ENTERING("1", "录入"),
    COUPONDUCT("2", "活动优惠券录入");

    private String value;
    private String note;

    EnumSource(String value, String note) {
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
