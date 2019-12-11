package com.github.auth.service;

import com.github.auth.feign.UserService;
import com.github.auth.constant.enums.EnumUserSource;
import com.github.auth.model.UserDetailsBo;
import com.github.common.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wsp
 * @date 2017/10/26
 * <p>
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetailsBo loadUserByUsername(String username) throws UsernameNotFoundException {

        String[] usernameArr=username.split("#");
        String loginForm= usernameArr[usernameArr.length-1];
        UserVO userVo =null;

        if(loginForm.equals("pwd")) {
            String finalUsername= usernameArr[0];
            //用户名-密码形式登陆
            if (EnumUserSource.ZL.getValue().equals(usernameArr[1])) {
                //职工-用户名和密码形式登陆
                userVo = userService.findUserByUsername(finalUsername);
            }
        }else if(loginForm.equals("mobile")) {
            String mobile= usernameArr[0];
            // 手机号-验证码形式登陆
            if (EnumUserSource.ZL.getValue().equals(usernameArr[1])) {
                // 职工-手机形式登陆
                userVo = userService.findUserByMobile(mobile);
            }
            //手机登陆形式-密码明文是123456
            userVo.setPassword("8d046f8f257479b744db7bcd2a15828c0397bdf9b795ff7abb8e38d1");
        }


        if(userVo.getSysRole()==null){
            log.error("UserDetailServiceImpl::loadUserByUsername::用户名："+userVo.getUsername()+"找不到角色");
        }

        log.info("userVo:"+userVo.toString());
        return new UserDetailsBo(
                userVo.getUserId(), username, userVo.getPassword(), userVo.getDelFlag(), userVo.getSysRole(),userVo.getCheckCustomerExist());
    }
}
