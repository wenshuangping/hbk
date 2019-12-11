package com.github.common.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author wsp
 * @since 2017-10-29
 */
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Data
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id")
    private Integer roleId;
    @TableField("role_name")
    private String roleName;
    @TableField("role_code")
    private String roleCode;
    @TableField("role_desc")
    private String roleDesc;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    /**
     * 部门id
     */
    @TableField("dept_id")
    private Integer deptId;

    /**
     * 地区id
     */
    @TableField("area_id")
    private String areaId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                ", roleId=" + roleId +
                ", roleName=" + roleName +
                ", roleCode=" + roleCode +
                ", roleDesc=" + roleDesc +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                ", deptId=" + deptId +
                ", areaId=" + areaId +
                "}";
    }
}