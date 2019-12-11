package com.admin.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("t_relation_log")
public class RelationLog extends Model<RelationLog> implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String customerId;

    private String customerName;

    private String customerMobile;

    private String merchantId;

    private String merchantMobile;

    private String batchId;

    private String managerId;

    private String updateBy;

    private Date updateDate;

    private String createBy;

    private Date createDate;

    private String provinceName;

    private String provinceId;

    private String cityId;

    private String cityName;

    private String processStatus;

    /**
     * 1: 已关联 0：未关联
     */
    private String relateStatus;

    /**
     * 1: 可被关联 0：不可被关联
     */
    @TableField(exist = false)
    private String statusName;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
