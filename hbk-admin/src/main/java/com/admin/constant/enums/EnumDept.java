package com.admin.constant.enums;

/**
 * @Author: wsp
 * @Date: 2018/12/4 17:50
 */
public enum EnumDept {

    COMPANY(0, "公司节点"),
    DEPT(1, "部门节点"),


    ERROR_ADD_3(3, "部门不能创建子节点");


    private Integer value;
    private String note;

    EnumDept(Integer value, String note) {
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
