package com.admin.model.dto;

import lombok.Data;

/**
 * @author wsp
 * @date 2018/1/20
 * 部门树
 */
@Data
public class DeptTree extends TreeNode {
    private String name;
    private String type;
    /**
     *  地区id
     */
    private String areaId;
}
