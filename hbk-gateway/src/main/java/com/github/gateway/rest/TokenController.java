package com.github.gateway.rest;

import com.github.gateway.constant.enums.EnumPwType;
import com.github.gateway.constant.enums.EnumUserSource;
import com.github.gateway.service.LoginService;
import com.github.common.constant.enums.EnumCode;
import com.github.gateway.component.dto.MobileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.gateway.component.dto.UserDto;

/**
 * @author: wsp
 * @date: 2018/1/16
 * @description: 令牌管理接口
 */
@RestController
public class TokenController {

    @Autowired
    private LoginService loginService;

    /**
     * 通过密码授权方式向授权服务器获取令牌: 后台管理
     * @param username
     * @param password
     * @param randomStr
     * @param code
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/zl/login")
    public ResponseEntity<OAuth2AccessToken> zlLogin(@RequestParam String username, @RequestParam String password,
                                                   @RequestParam String randomStr, @RequestParam String code) throws Exception {
        // 数据映射
        UserDto userDto = new UserDto();
        userDto.setUsername(username+"#"+ EnumUserSource.ZL.getValue()+"#pwd");
        userDto.setPassword(password);
        userDto.setRandomStr(randomStr);
        userDto.setCode(code);
        userDto.setPwType(EnumPwType.AES.getValue());
        userDto.setSource(EnumUserSource.ZL.getValue());
        return loginService.doValidateLogin(userDto);
    }

    /**
     * 通过手机验证码方式向授权服务器获取令牌: 后台管理
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/zl/mobile")
    public ResponseEntity<OAuth2AccessToken> zlMobileLogin(@RequestParam String mobile,@RequestParam String code){
        MobileDto mobileDto = new MobileDto();
        mobileDto.setMobile(mobile);
        mobileDto.setCode(code);
        return loginService.doMobileLogin(mobileDto,EnumCode.DEFAULT_CODE_KEY.getValue(),EnumUserSource.ZL.getValue());
    }



}
