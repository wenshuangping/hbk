package com.github.common.util;

import com.github.common.vo.SysRole;
import com.github.common.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wsp
 * @date 2018-09-19 20:48
 */
@Slf4j
public class UserVoUtil {


    /**
     * null 字符串
     */
    public static String NULL_STR = "null";

    /**
     * spring security 框架内部分割字段采用了逗号，因此不能使用json格式。采用自定义的文本格式
     * 格式：roleCode:deptId:roleSource
     *
     * @param sysRole 角色
     * @return
     */
    public static String userVO2String(SysRole sysRole,String userid) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sysRole.getRoleCode()).append(":");
        stringBuffer.append(sysRole.getDeptId()).append(":");
        stringBuffer.append(userid).append(":");
        stringBuffer.append(sysRole.getAreaId());
        log.info("--------------------- userVO2String  sysRoleStr-------------------:"+stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * 解释：字符串转化成SysRole对象
     *
     * @param sysRoleStr 角色
     * @return
     */
    public static UserVO str2UserVO(String sysRoleStr) {
        UserVO  userVO = new UserVO();
        log.info("---------------------str2UserVO  sysRoleStr-------------------#"+sysRoleStr);
        String[] sysRoleArr = sysRoleStr.split(":");
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode(NULL_STR.equals(sysRoleArr[0]) ? null : sysRoleArr[0]);
        sysRole.setDeptId(NULL_STR.equals(sysRoleArr[1]) ? null : Integer.parseInt(sysRoleArr[1]));
        sysRole.setAreaId(NULL_STR.equals(sysRoleArr[3]) ? null : sysRoleArr[3]);

        userVO.setSysRole(sysRole);
        userVO.setUserId(NULL_STR.equals(sysRoleArr[2]) ? null : sysRoleArr[2]);
        return userVO;
    }


    public static void main(String[] args) {

        str2UserVO("CUSTOMER_MANAGER:19:773bb1786ba5462fa1d84efd58c19456:null");


    }

}
