package com.admin.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author wsp
 * @since 2018-01-22
 */
@Data
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;
    /**
     * 部门|公司名称
     */
    private String name;
    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 是否删除  -1：已删除  0：正常
     */
    @TableField("del_flag")
    private String delFlag;

    @TableField("parent_id")
    private Integer parentId;

    /**
     *  地区id
     */
    @TableField("area_id")
    private String areaId;

    /**
     *  节点类型:0公司节点，1部门节点
     */
    @TableField("type")
    private String type;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;


    @Override
    protected Serializable pkVal() {
        return this.deptId;
    }

    @Override
    public String toString() {
        return "SysDept{" +
                ", deptId=" + deptId +
                ", name=" + name +
                ", orderNum=" + orderNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
