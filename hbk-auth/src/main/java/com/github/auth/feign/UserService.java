package com.github.auth.feign;

import com.github.auth.feign.fallback.UserServiceFallbackImpl;
import com.github.common.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wsp
 * @date 2017/10/31
 */
@FeignClient(contextId = "UserService", value = "mika-admin",fallback = UserServiceFallbackImpl.class)
public interface UserService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping("/user/findUserByUsername/{username}")
    UserVO findUserByUsername(@PathVariable("username") String username);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     * @return UserVo
     */
    @GetMapping("/user/findUserByMobile/{mobile}")
    UserVO findUserByMobile(@PathVariable("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     * @return UserVo
     */
    @GetMapping("/user/findUserByOpenId/{openId}")
    UserVO findUserByOpenId(@PathVariable("openId") String openId);
}
