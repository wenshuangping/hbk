package com.admin.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author wsp
 * @date 2018/1/20
 * 角色Dto
 */
@Data
public class RoleDTO  {
    /**
     * 序号
     */
    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String roleDesc;
    /**
     * 部门id
     */
    private Integer deptId;
    /**
     * 地区id
     */
    private String areaId;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 创建日期时间
     */
    private Date   createTime;
}
