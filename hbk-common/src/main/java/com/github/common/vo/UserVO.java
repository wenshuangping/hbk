

package com.github.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wsp
 * @date 2017/10/29
 */
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 随机盐
     */
    private String salt;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 0-正常，1-删除
     */
    private String delFlag;
    /**
     * 简介
     */
    private String phone;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 翼米网厅检查用户是否存在：支付宝和微信专用
     */
    private Boolean checkCustomerExist;

    /**
     * 节点类型:0公司节点，1部门节点
     */
    //private Integer type;

    /**
     * 部门名称
     */
    //private String deptName;

    /**
     * 角色列表
     */
    private SysRole sysRole;

}
