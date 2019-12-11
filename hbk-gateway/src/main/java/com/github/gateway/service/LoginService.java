
package com.github.gateway.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.github.gateway.component.dto.MobileDto;
import com.github.gateway.component.dto.UserDto;

public interface LoginService {


    /**
     * 验证码登陆
     * @param userDto
     * @return
     */
    ResponseEntity<OAuth2AccessToken> doValidateLogin(UserDto userDto) throws Exception  ;



    /**
     * 用户名和密码形式登陆
     *
     * @param userDto
     *
     * @return
     * @throws Exception
     */
    ResponseEntity doLogin(UserDto userDto) ;


    /**
     * 手机和验证码形式登陆
     *
     * @param mobileDto
     * @param prefix 前缀
     * @param source 用户来源
     * @return
     * @throws Exception
     */
    ResponseEntity doMobileLogin(MobileDto mobileDto,String prefix,String source);

    /**
     * 手机形式登陆
     * 微信授权成功后,Zuul后置Filter调用,不需要验证码
     * @param mobileDto
     * @param source 用户来源
     */
    ResponseEntity<OAuth2AccessToken> doMobileLogin(MobileDto mobileDto,String source) ;



    /**
     * 刷新token
     * @param refreshToken
     * @return
     * @throws Exception
     */
    ResponseEntity doRefreshToken (String refreshToken);





}


