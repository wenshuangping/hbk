package com.admin.service.impl;

import com.admin.constant.enums.EnumRole;
import com.admin.mapper.SysRoleMapper;
import com.admin.model.dto.RoleDTO;
import com.admin.service.SysDeptService;
import com.admin.service.SysRoleService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.common.bean.interceptor.DataScope;
import com.github.common.constant.enums.EnumDelFlag;
import com.github.common.util.Query;
import com.github.common.vo.SysRole;

import com.github.common.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wsp
 * @since 2017-10-29
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    @Override
    public Boolean insertRole(RoleDTO roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(new Date());
        sysRole.setDelFlag(EnumDelFlag.UNDELETE.getValue());
        sysRoleMapper.insert(sysRole);
        return true;
    }

    /**
     * 分页查角色列表
     *
     * @param query   查询条件
     * @param userVO
     * @return page
     */
    @Override
    public Page<Object> selectwithDeptPage(Query<Object> query, UserVO userVO) {
        DataScope dataScope=null;
        if(!userVO.getSysRole().getRoleCode().equals(EnumRole.ROLE_ADMIN.getValue())){
            dataScope = new DataScope();
            dataScope.setScopeName("dept_id");
            dataScope.setIsOnly(true);
            dataScope.setDeptIds( sysDeptService.getUserAllDepts(userVO));
        }

        query.setRecords(sysRoleMapper.selectRolePage(query, query.getCondition(),dataScope));
        return query;
    }

    /**
     * 更新角色
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateRoleById(RoleDTO roleDto) {
        // 更新角色信息
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.updateById(sysRole);
        return true;
    }

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectListByDeptId(Integer deptId) {
        return sysRoleMapper.selectListByDeptId(deptId);
    }
}
