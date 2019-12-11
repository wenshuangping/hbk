package com.admin.service;

import com.github.common.util.Res;
import com.github.common.util.exception.ValidateCodeException;

/**
 * @author wsp
 */
public interface ValidateCodeService {
    String createCode(String randomStr) throws Exception;


    /**
     * 发送验证码
     *
     * @param mobile 手机号
     * @param prefix 前缀
     * @return R
     */
    Res<Boolean> sendSmsCode(String mobile, String prefix);


    /**
     * 验证码比较
     * @param key
     * @param code
     * @throws ValidateCodeException
     */
    void check(String key, String code) throws ValidateCodeException ;



    /**
     *
     * @param mobile
     * @return
     */
    Res generateVerifyCodeByReg(String mobile);

}
