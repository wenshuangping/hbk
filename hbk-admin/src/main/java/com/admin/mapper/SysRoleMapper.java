package com.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.common.bean.interceptor.DataScope;
import com.github.common.util.Query;
import com.github.common.vo.SysRole;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wsp
 * @since 2017-10-29
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询角色列表含有部门信息
     *
     * @param query     查询对象
     * @param condition 条件
     * @return List
     */
    List<Object> selectRolePage(Query<Object> query, Map<String, Object> condition, DataScope dataScop);

    /**
     * 通过部门ID查询角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    List<SysRole> selectListByDeptId(Integer deptId);
}