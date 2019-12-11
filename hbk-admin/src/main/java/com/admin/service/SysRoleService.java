package com.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.github.common.util.Query;
import com.github.common.vo.SysRole;

import com.github.common.vo.UserVO;
import com.admin.model.dto.RoleDTO;


import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wsp
 * @since 2017-10-29
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 添加角色
     *
     * @param roleDTO 角色信息
     * @return 成功、失败
     */
    Boolean insertRole(RoleDTO roleDTO);

    /**
     * 分页查角色列表
     *
     * @param objectQuery         查询条件
     * @param userVO
     * @return page
     */
    Page selectwithDeptPage(Query<Object> objectQuery, UserVO userVO);

    /**
     * 更新角色
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    Boolean updateRoleById(RoleDTO roleDto);

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    List<SysRole> selectListByDeptId(Integer deptId);
}
