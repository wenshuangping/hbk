package com.github.gateway.service.impl;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.github.common.util.UserVoUtil;
import com.github.common.vo.MenuVO;
import com.github.common.vo.UserVO;
import com.github.gateway.feign.MenuService;
import com.github.gateway.service.PermissionService;
import com.xiaoleilu.hutool.collection.CollUtil;
import com.xiaoleilu.hutool.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wsp
 * @date 2017/10/28
 */
@Slf4j
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private MenuService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        log.info(authentication.getName()+": request.getRequestURI() :"+request.getRequestURI());
        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null && (!"anonymousUser".equals(principal)) ) {
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }

            Set<MenuVO> urls = new HashSet<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                UserVO userVO  = UserVoUtil.str2UserVO(authority.getAuthority());

                Set<MenuVO> menuVOSet = menuService.findMenuByRole(userVO.getSysRole().getRoleCode());
                if (CollUtil.isNotEmpty(menuVOSet)) {
                    CollUtil.addAll(urls, menuVOSet);
                }
            }

            for (MenuVO menu : urls) {
                if (StringUtils.isNotEmpty(menu.getUrl()) && antPathMatcher.match(menu.getUrl(), request.getRequestURI())
                        && request.getMethod().equalsIgnoreCase(menu.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }

        if(!hasPermission){
            log.debug("-----------------权授失败 url:"+request.getRequestURI());
        }



        return hasPermission;
    }
}





