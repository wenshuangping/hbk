package com.github.auth.model;

import com.github.common.constant.CommonConstant;
import com.github.common.util.UserVoUtil;
import com.github.common.vo.SysRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wsp
 * @date 2017/10/29
 */
public class UserDetailsBo implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String password;
    private String status;
    private SysRole role;
    private String username;
    /**
     * 翼米网厅检查用户是否存在：支付宝和微信专用
     */
    private Boolean checkCustomerExist;

    public UserDetailsBo(String userId,String username, String password, String status, SysRole role,Boolean checkCustomerExist) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
        this.username=username;
        this.checkCustomerExist=checkCustomerExist;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(UserVoUtil.userVO2String(role,userId)));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return StringUtils.equals(CommonConstant.STATUS_LOCK, status) ? false : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(CommonConstant.STATUS_NORMAL, status) ? true : false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Boolean getCheckCustomerExist(){
       return  this.checkCustomerExist;
    }

}
