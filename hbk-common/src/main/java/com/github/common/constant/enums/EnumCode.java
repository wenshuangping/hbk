package com.github.common.constant.enums;

public enum EnumCode {
    DEFAULT_CODE_KEY("DEFAULT_CODE_KEY", "默认保存code的前缀"),
    CUSTOMER_CODE_KEY("CUSTOMER_CODE_KEY", "客户"),
    BUSINESS_REGISTER_CODE_KEY("BUSINESS_REGISTER_CODE_KEY", "客户经理|商户注册"),
    BUSINESS_CODE_KEY("BUSINESS_CODE_KEY", "客户经理/商户"),
    QR_CODE_PREFIX("QR_CODE:", "QR码内容前缀"),
    ALI_USERID("ALI_USERID:", "支付宝aliId前缀,key为sessonId,value为aliId"),
    WX_OPENID("WX_OPENID:", "微信openid前缀,key为sessonId,value为openid");

    private String value;
    private String note;

    EnumCode(String value, String note) {
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
