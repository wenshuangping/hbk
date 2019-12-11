package com.github.gateway.constant.enums;

/**
 * @Author: wsp
 * @Date: 2018/12/7 16:57
 */
public enum EnumLoginCode {

    NamePwdError("600", "用户名或密码错误"),
    CustomerLoginNoFindMobile("601", "该手机号尚未被注册"),
    ValidateCode("607", "验证码错误，请重新输入"),
    CustomerLoginNoFindName("608", "该用户名尚未被注册"),

    RefreshTokenError("701", "refresh_token 过期")
    ;

    private String value;
    private String note;

    EnumLoginCode(String value, String note) {
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
