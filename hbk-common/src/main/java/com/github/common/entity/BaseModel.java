package com.github.common.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 基础model
 *
 * @Author: wsp
 *
 * @Date: 2019/1/10 16:25
 */
@Data
public abstract class BaseModel<T extends Model> extends Model<T> {

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 删标标记
     */
    private String delFlag;



}
