package com.github.auth.controller;

import com.github.common.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author wsp
 * @date 2018年03月10日
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    @Resource
    private RedisTemplate<String ,Object> redisTemplate;

    /**
     * 认证页面
     *
     * @return ModelAndView
     */
    @GetMapping("/require")
    public ModelAndView require() {
        return new ModelAndView("ftl/login");
    }

    /**
     * 用户信息校验
     *
     * @param authentication 信息
     * @return 用户信息
     */
    @RequestMapping("/user")
    public Object user(Authentication authentication) {
        return authentication.getPrincipal();
    }

    /**
     * 清除Redis中 accesstoken refreshtoken
     *
     * @param authorization authorization
     * @return true/false
     */
    @PostMapping("/removeToken")
    public Res<Boolean> removeToken(@RequestHeader(value="Authorization")  String authorization) {
        String accesstoken=authorization.substring(7);
        boolean flag= consumerTokenServices.revokeToken(accesstoken);
        Res res = new Res();
        if(!flag){
            //res.setCode(Res.FAIL);
            //res.setMsg("fail");
        }
        res.setData(flag);
        return res;
    }

    @PostMapping("/removeTokenByMobile")
    public void removeTokenByMobile(@RequestParam("mobile") String mobile){
        String key="zlmsf_uname_to_access:pig:"+mobile+"#business#pwd";
        //List<DefaultOAuth2AccessToken> b= (List)redisTemplate.opsForList().rightPop(key);
        DefaultOAuth2AccessToken defaultOAuth2AccessToken= (DefaultOAuth2AccessToken)redisTemplate.opsForList().rightPop(key);
        if(defaultOAuth2AccessToken!=null) {
            String accesstoken = defaultOAuth2AccessToken.getValue();
            boolean flag = consumerTokenServices.revokeToken(accesstoken);
            System.out.println();
        }
    }




}