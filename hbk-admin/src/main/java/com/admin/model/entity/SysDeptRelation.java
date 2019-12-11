package com.admin.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wsp
 * @since 2018-01-22
 */
@Data
@TableName("sys_dept_relation")
public class SysDeptRelation extends Model<SysDeptRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 祖先节点
     */
    @TableId(value = "ancestor",type = IdType.INPUT)
    private Integer ancestor;
    /**
     * 后代节点
     */
    private Integer descendant;


    @Override
    protected Serializable pkVal() {
        return this.ancestor;
    }

    @Override
    public String toString() {
        return "SysDeptRelation{" +
                ", ancestor=" + ancestor +
                ", descendant=" + descendant +
                "}";
    }
}
