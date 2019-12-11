package com.github.common.exception;

/**
 * 翼米网厅登陆：用户名找不到
 * @Author: wsp
 * @Date: 2019/1/17 15:18
 */
public class CustomerLoginNoFindNameException extends  RuntimeException {

    public CustomerLoginNoFindNameException(String message){
        super(message);
    }

}
