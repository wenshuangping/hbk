package com.github.gateway.service;

import com.github.common.util.exception.ValidateCodeException;


public interface ValidateCodeService {


    /**
     *检查验证码
     */
    void check(String key, String value) throws ValidateCodeException;

}
