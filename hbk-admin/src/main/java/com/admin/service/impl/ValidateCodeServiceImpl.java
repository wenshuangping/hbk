package com.admin.service.impl;

import com.admin.service.ValidateCodeService;
import com.github.common.constant.SecurityConstants;
import com.github.common.constant.enums.EnumCode;
import com.github.common.util.Res;
import com.github.common.util.exception.ValidateCodeException;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MINUTES;


@Slf4j
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    private static final String EXPIRED_CAPTCHA_ERROR = "验证码已过期，请重新获取";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成数字验证码
     *
     * @param randomStr 随机数(前端转输过来的)
     */
    @Override
    public String createCode(String randomStr) throws Exception {
        // 生成文字验证码
        String text = format("%04d", new Random().nextInt(9999));
        this.saveImageCode(randomStr, text);
        return text;
    }

    /**
     * 保存用户验证码，和randomStr绑定
     *
     * @param randomStr 客户端生成
     * @param imageCode 验证码信息
     */
    private void saveImageCode(String randomStr, String imageCode) {
        redisTemplate.opsForValue().set(EnumCode.DEFAULT_CODE_KEY.getValue() + randomStr, imageCode,
                SecurityConstants.DEFAULT_IMAGE_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 发送验证码
     * <p>
     * 1. 先去redis 查询是否 60S内已经发送 2. 未发送： 判断手机号是否存 ? false :产生4位数字 手机号-验证码 3.
     * 发往消息中心-》发送信息 4. 保存redis
     *
     * @param mobile 手机号
     * @return true、false
     */
    @Override
    public Res sendSmsCode(String mobile, String prefix) {
        return  saveRedisAndSms(mobile,prefix);
    }

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
            throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
        }

        // 前后端验证码对比
        if (!StrUtil.equals(saveCode, code)) {
            throw new ValidateCodeException("验证码错误，请重新输入");
        }
        //log.info("短信发送请求消息中心 ->  key:{} -> 验证码：{}-> 验证码redis:{}", key, code,saveCode);

        // 删除过期验证码
        // redisTemplate.delete(key);
    }

    /**
     * 生成验证码,发短信/存Redis
     * @param mobile
     * @return
     */
    @Override
    public Res generateVerifyCodeByReg(String mobile) {
       return saveRedisAndSms(mobile,"");
    }

    /**
     * 保存到redis并
     * @param mobile
     */
    private Res saveRedisAndSms(String mobile,String prefix){

        Res res = new Res();
        String reg = "^[1]\\d{10}$";
        if ( (!StringUtils.isEmpty(mobile)) && mobile.matches(reg)) {

            String code = format("%06d", new Random().nextInt(999999));
            // 验证码存入Redis
            redisTemplate.opsForValue().set(mobile, code, 10, MINUTES);
            redisTemplate.opsForValue().set(prefix + mobile, code,10, TimeUnit.MINUTES);

           // log.info("code:"+code);
           // log.info("短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobile, code);
           // log.info("短信发送请求消息中心 ->  key:{} -> 验证码：{}", (prefix + mobile), code);

            //发送手机短信
            Map<String, String> reqMap = new HashMap<>();
            reqMap.put("mobile",mobile);
            reqMap.put("signName","翼米网厅");
            reqMap.put("tmpCode","SMS_148075474");
            reqMap.put("template","{\"code\":\""+code+"\"}");



            res.setMsg("短信发送成功，请查收");
            return res;
        }
        res.setCode(1);
        res.setMsg("短信发送失败");
        return  res;

    }



}
