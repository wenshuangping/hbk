package com.admin.model.dto;

import com.admin.model.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @author wsp
 * @date 2017/11/5
 */
@Data
public class UserDTO extends SysUser {
    /**
     * 角色ID
     */
    private List<Integer> role;

    private Integer deptId;

    /**
     * 前端转输的验证码
     */
    private String verifyCode;

    /**
     * 随机数验证
     */
    private String randomStr;


    /**
     * 新密码
     */
    private String newpassword1;


    /**
     * 地区ID
     */
    private String areaId;
}
