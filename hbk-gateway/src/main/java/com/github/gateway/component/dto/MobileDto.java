package com.github.gateway.component.dto;

import java.io.Serializable;

import lombok.Data;


/**
 * @author: wsp
 * @date: 2018/1/15
 * @description: 手机登录数据传输对象
 */
@Data
public class MobileDto implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -6945135629989858523L;
    //手机号码
    private String mobile;

    //验证码
    private String code;


}
