package com.admin.rest;

import com.github.common.constant.enums.EnumCode;
import com.admin.service.ValidateCodeService;
import com.github.common.constant.SecurityConstants;
import com.github.common.util.Assert;
import com.github.common.util.Res;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @author wsp
 * @date 2017/12/18 验证码提供
 */
@Controller
public class ValidateCodeController {

    @Autowired
    private Producer producer;

    @Autowired
    private ValidateCodeService validateCodeIService;

    /**
     * 创建验证码
     *
     * @param request request
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{randomStr}")
    public void createCode(@PathVariable String randomStr, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //生成文字验证码
        String text = validateCodeIService.createCode(randomStr);

        // 生成图片验证码
        Assert.isBlank(randomStr, "机器码不能为空");
        response.setHeader("Cache-Control", "no-shop, no-cache");
        response.setContentType("image/jpeg");

        BufferedImage image = producer.createImage(text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);

        //关闭
        out.close();
    }

    /**
     * 发送手机验证码：后台
     *
     * @param mobile 手机号
     * @return R
     */
    @ResponseBody
    @GetMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX + "/{mobile}")
    public Res<Boolean> createCode(@PathVariable String mobile) {
        Assert.isBlank(mobile, "手机号不能为空");
        return validateCodeIService.sendSmsCode(mobile,EnumCode.DEFAULT_CODE_KEY.getValue());
    }

    /**
     * 发送手机验证码:客户
     *
     * @param mobile 手机号
     * @return R
     */
    @ResponseBody
    @PostMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX + "/customer/{mobile}")
    public Res<Boolean> createCustomerCode(@PathVariable String mobile) {
        Assert.isBlank(mobile, "手机号不能为空");
        return validateCodeIService.sendSmsCode(mobile,EnumCode.CUSTOMER_CODE_KEY.getValue());
    }

    /**
     * 发送手机验证码:客户经理
     *
     * @param mobile 手机号
     * @return R
     */
    @ResponseBody
    @PostMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX + "/business/{mobile}")
    public Res<Boolean> createBusinessLoginCode(@PathVariable String mobile) {
        Assert.isBlank(mobile, "手机号不能为空");
        return validateCodeIService.sendSmsCode(mobile,EnumCode.BUSINESS_CODE_KEY.getValue());
    }

    /**
     * 发送手机验证码:客户经理
     *
     * @param mobile 手机号
     * @return R
     */
    @ResponseBody
    @PostMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX + "/business/register/{mobile}")
    public Res<Boolean> createBusinessRegisterCode(@PathVariable String mobile) {
        Assert.isBlank(mobile, "手机号不能为空");
        return validateCodeIService.sendSmsCode(mobile,EnumCode.BUSINESS_REGISTER_CODE_KEY.getValue());
    }


    /**
     *  忘记密码获取验证码
     */
    @ResponseBody
    @PostMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX +"/business/mobile/verifyCode")
    public Res getVerifyCode(@RequestParam String mobile) {
        return validateCodeIService.generateVerifyCodeByReg(mobile);
    }


    /**
     * 换绑手机获取验证码
     */
    @ResponseBody
    @PostMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX +"/business/changeMobile/verifyCode")
    public Res getChangeMobileVerifyCode(@RequestParam String mobile) {
        return validateCodeIService.generateVerifyCodeByReg(mobile);
    }

}
