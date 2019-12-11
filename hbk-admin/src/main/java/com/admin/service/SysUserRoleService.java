package com.admin.service;


import com.baomidou.mybatisplus.service.IService;
import com.admin.model.entity.SysUserRole;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author wsp
 * @since 2017-10-29
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author 寻欢·李
     * @date 2017年12月7日 16:31:38
     */
    Boolean deleteByUserId(String userId);
}
