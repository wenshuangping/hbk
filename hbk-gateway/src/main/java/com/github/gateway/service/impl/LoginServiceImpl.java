package com.github.gateway.service.impl;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.github.gateway.constant.enums.EnumLoginCode;
import com.github.gateway.constant.enums.EnumPwType;
import com.github.common.constant.enums.EnumCode;
import com.github.common.exception.CustomerLoginNoFindMobileException;
import com.github.common.exception.CustomerLoginNoFindNameException;
import com.github.common.util.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.github.gateway.component.dto.MobileDto;
import com.github.gateway.component.dto.UserDto;
import com.github.gateway.service.LoginService;
import com.github.gateway.service.ValidateCodeService;
import com.xiaoleilu.hutool.util.CharsetUtil;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NOPadding";

    // 解密的key
    @Value("${security.encode.key}")
    private String key;

    // 手机的方式访问token的uri
    // @Value("${security.oauth2.client.access-mobile-token-uri}")
    // private String accessMobileTokenUri;

    // oauth2属性
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    // restful方式发送客户端
    @Autowired
    private RestTemplate restTemplate;

    // 验证码检查
    @Autowired
    private ValidateCodeService validateCodeService;




    @Override
    public ResponseEntity<OAuth2AccessToken> doValidateLogin(UserDto userDto)
            throws Exception {
        // 验证码检查
        validateCodeService.check(EnumCode.DEFAULT_CODE_KEY.getValue()+  userDto.getRandomStr(),userDto.getCode());
        //登陆
        return doLogin(userDto);

    }

    /**
     * 用户名和密码登陆
     * @param userDto
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity doLogin(UserDto userDto){

        // 转发到auth服务
        try{
            return  execLogin(userDto);
        }catch (CustomerLoginNoFindNameException e){
            Map<String ,String> resultMap = new HashMap<>(2);
            resultMap.put("code", EnumLoginCode.CustomerLoginNoFindName.getValue());
            resultMap.put("msg",e.getMessage());
            return ResponseEntity.ok(resultMap);
        }catch(Exception e){
            log.error("LoginServiceImpl.doLogin",e);
            Map<String ,String> resultMap = new HashMap<>(2);
            resultMap.put("code",EnumLoginCode.NamePwdError.getValue());
            resultMap.put("msg","用户或密码有误");
            return ResponseEntity.ok(resultMap);
        }
    }

    private  ResponseEntity execLogin(UserDto userDto) throws Exception {
        if(EnumPwType.AES.getValue().equals( userDto.getPwType() )) {
            // 解密
            String password = decryptAES(userDto.getPassword(), key).trim();
            userDto.setPassword(password);
        }
        // Http Basic 验证
        String clientAndSecret = oAuth2ClientProperties.getClientId() + ":" + oAuth2ClientProperties.getClientSecret();

        // 这里需要注意为 Basic 而非 Bearer
        clientAndSecret = "Basic " + Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", clientAndSecret);

        // 授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(userDto.getUsername()));
        map.put("password", Collections.singletonList(userDto.getPassword()));
        map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
        map.put("scope", oAuth2ProtectedResourceDetails.getScope());

        return sendAuthService(oAuth2ProtectedResourceDetails.getAccessTokenUri(), map, httpHeaders);
    }

    /**
     * 手机和验证码形式登陆
     *
     * @param mobileDto
     * @param prefix 前缀
     * @param source 来源
     * @return
     * @throws Exception
     */
    @Override
    public ResponseEntity doMobileLogin(MobileDto mobileDto,String prefix,String source) {
        try{
            // 验证码检查
            validateCodeService.check(mobileDto.getMobile(),mobileDto.getCode());

            UserDto userDto = new UserDto();
            userDto.setUsername(mobileDto.getMobile()+"#"+source+"#"+"mobile");
            //手机登陆走用户名密码
            userDto.setPassword("123456");
            userDto.setPwType(EnumPwType.CLEARTEXT.getValue());
            return this.execLogin(userDto);
        }catch (ValidateCodeException e){
            e.printStackTrace();
            //验证码
            Map<String ,String> resultMap = new HashMap<>(2);
            resultMap.put("code",EnumLoginCode.ValidateCode.getValue());
            resultMap.put("msg",e.getMessage());
            return ResponseEntity.ok(resultMap);
        }catch (CustomerLoginNoFindMobileException e) {
            e.printStackTrace();
            //翼米网厅：手机号登陆时，手机未注册
            Map<String ,String> resultMap = new HashMap<>(2);
            resultMap.put("code",EnumLoginCode.CustomerLoginNoFindMobile.getValue());
            resultMap.put("msg",e.getMessage());
            return ResponseEntity.ok(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            Map<String ,String> resultMap = new HashMap<>(2);
            resultMap.put("code",EnumLoginCode.NamePwdError.getValue());
            resultMap.put("msg",e.getMessage());
            return ResponseEntity.ok(resultMap);
        }
    }

    /**
     * 手机形式登陆
     * 微信授权成功后,Zuul后置Filter调用,不需要验证码
     * @param mobileDto
     * @param source 用户来源
     */
    @Override
    public ResponseEntity doMobileLogin(MobileDto mobileDto, String source) {
        UserDto userDto = new UserDto();
        userDto.setUsername(mobileDto.getMobile()+"#"+source+"#"+"mobile");
        //手机登陆走用户名密码
        userDto.setPassword("123456");
        userDto.setPwType(EnumPwType.CLEARTEXT.getValue());

        ResponseEntity responseEntity =null;
        try {
            responseEntity=this.doLogin(userDto);
        } catch (Exception e) {
            log.error("LoginServiceImpl:doMobileLogin:",e);
        }
        return  responseEntity;

    }


    /**
     * 刷新re
     * @param refreshToken
     * @return
     */
    @Override
    public ResponseEntity doRefreshToken(String refreshToken) {

        // 授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("refresh_token", Collections.singletonList(refreshToken));
        map.put("client_id",Collections.singletonList(oAuth2ClientProperties.getClientId()));
        map.put("grant_type", Collections.singletonList("refresh_token"));
        map.put("client_secret",Collections.singletonList(oAuth2ClientProperties.getClientSecret()));

        try{
            // 转发到auth服务
            ResponseEntity  responseEntity = sendAuthService(oAuth2ProtectedResourceDetails.getAccessTokenUri(), map, null);
            return  responseEntity;
        }catch (Exception e){
            e.printStackTrace();

            Map<String ,String> resultMap = new HashMap<>(2);
            resultMap.put("code", EnumLoginCode.RefreshTokenError.getValue());
            resultMap.put("msg","refresh_token 过期");
            return ResponseEntity.ok(resultMap);
        }
    }


    /**
     * 发送给认证服务
     *
     * @param map
     * @param httpHeaders
     * @return
     */
    private ResponseEntity<OAuth2AccessToken> sendAuthService(String url, MultiValueMap<String, String> map,
                                                              HttpHeaders httpHeaders) {
        // HttpEntity
        HttpEntity httpEntity = new HttpEntity(map, httpHeaders);

        // 获取 token
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                OAuth2AccessToken.class);
    }

    /**
     * 解密
     *
     * @param data
     * @param pass
     * @return
     * @throws Exception
     */
    private String decryptAES(String data, String pass) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        SecretKeySpec keyspec = new SecretKeySpec(pass.getBytes(), KEY_ALGORITHM);
        IvParameterSpec ivspec = new IvParameterSpec(pass.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        byte[] result = cipher.doFinal(com.xiaoleilu.hutool.codec.Base64.decode(data.getBytes(CharsetUtil.UTF_8)));
        return new String(result, CharsetUtil.UTF_8);
    }
}
