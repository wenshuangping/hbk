package com.github.gateway.service.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.common.util.exception.ValidateCodeException;
import com.github.gateway.service.ValidateCodeService;
import com.xiaoleilu.hutool.util.StrUtil;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    private static final String EXPIRED_CAPTCHA_ERROR = "验证码已过期，请重新获取";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 验证码对比
     *
     * @param key
     * @param code
     */
    @Override
    public void check(String key, String code) throws ValidateCodeException {
        if (!redisTemplate.hasKey(key)) {
            throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
        }
        String saveCode = redisTemplate.opsForValue().get(key);
        if (saveCode == null) {
            throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
        }
        if (StrUtil.isBlank(saveCode)) {
            //redisTemplate.delete(key);
            throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
        }

        // 前后端验证码对比
        if (!StrUtil.equals(saveCode, code)) {
            // redisTemplate.delete(key);
            throw new ValidateCodeException("验证码错误，请重新输入");
        }

        // 删除过期验证码
        //redisTemplate.delete(key);
    }


}
