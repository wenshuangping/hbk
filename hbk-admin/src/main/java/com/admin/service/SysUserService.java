package com.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.github.common.util.Query;
import com.github.common.util.Res;
import com.github.common.vo.UserVO;

import com.admin.model.dto.UserDTO;
import com.admin.model.dto.UserInfo;
import com.admin.model.entity.SysUser;

/**
 * @author wsp
 * @date 2017/10/31
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名查询用户角色信息
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO findUserByUsername(String username);

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param query  查询条件
     * @param userVO
     * @return
     */
    Page selectWithRolePage(Query query, UserVO userVO);

    /**
     * 查询用户信息
     *
     * @param userVo 角色名
     * @return userInfo
     */
    UserInfo findUserInfo(UserVO userVo);


    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return boolean
     */
    Boolean deleteUserById(SysUser sysUser);

    /**
     * 更新当前用户基本信息
     *
     * @param userDto  用户信息
     * @return Boolean
     */
    Res<Boolean> updateUserInfo(UserDTO userDto, UserVO userVO);

    /**
     * 更新指定用户信息
     *
     * @param userDto  用户信息
     * @param username 用户信息
     * @return
     */
    Boolean updateUser(UserDTO userDto, String username);

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    UserVO findUserByMobile(String mobile);



    /**
     * 通过openId查询用户
     *
     * @param openId openId
     * @return 用户信息
     */
    UserVO findUserByOpenId(String openId);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO selectUserVoById(String id);

    /**
     * 用户（mika用户,客户经理|商户）注册
     *
     * @param userDTO
     * @return
     */
    Res<Object> register(UserDTO userDTO);


    /**
     * 用户（mika用户,客户经理|商户）修改手机号码
     *
     * @param userDTO
     * @return
     */
    void changePhone(UserDTO userDTO);


    /**
     * 将Controller方法抽取到Service
     * @param userDto
     * @return
     */
    String addUserForCustomerManager(UserDTO userDto);

    /**
     * REST-通过sys_user主键查找username
     */
    String selectUsernameById(String userId);

}
