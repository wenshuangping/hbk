package com.github.common.exception;

/**
 * 翼米网厅登陆：手机号码找不到
 * @Author: wsp
 * @Date: 2019/1/17 15:18
 */
public class CustomerLoginNoFindMobileException extends  RuntimeException {

    public CustomerLoginNoFindMobileException(String message){
        super(message);
    }

}
