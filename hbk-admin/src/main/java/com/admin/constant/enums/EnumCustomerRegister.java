package com.admin.constant.enums;

/**
 * @Author: wsp
 * @Date: 2018/12/4 17:50
 */
public enum EnumCustomerRegister {

    CustomerCoupon(801, "该手机号通过参与活动系统默认注册，请通过验证码登陆或者忘记密码重新设置密码");

    private Integer value;
    private String note;

    EnumCustomerRegister(Integer value, String note) {
        this.value = value;
        this.note = note;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
