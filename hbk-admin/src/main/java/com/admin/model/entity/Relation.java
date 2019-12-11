package com.admin.model.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("t_relation")
public class Relation implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String managerId;

    private String merchantId;

    private String customerId;

    private String updateBy;

    private Date updateDate;

    private String createBy;

    private Date createDate;

    /**
     * 1:直接关联，0:间接关联
     */
    @TableField(exist = false)
    private String relation;

    /**
     * 关联类型 商户0 客户1
     */
    public String getRelation() {
        return StringUtils.isEmpty(merchantId) ? "1" : "0";
    }

}
