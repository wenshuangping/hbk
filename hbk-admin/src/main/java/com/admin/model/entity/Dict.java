package com.admin.model.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.github.common.entity.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wsp
 * @Date: 2019/1/21 17:10
 */

@Data
@TableName("t_dict")
public class Dict extends BaseModel<Dict> {

    private String   priId;//编号
    private String   value;//数据值
    private String   label;//标签名
    private String   type;//类型
    private String   description;//描述
    private String   sort;//排序（升序）
    private String   parentId;//父级编号
    private String   remarks;//备注信息


    @Override
    protected Serializable pkVal() {
        return this.priId;
    }
}
