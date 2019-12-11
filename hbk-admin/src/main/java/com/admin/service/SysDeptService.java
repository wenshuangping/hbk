package com.admin.service;

import com.admin.model.entity.SysDept;
import com.baomidou.mybatisplus.service.IService;
import com.github.common.util.Res;
import com.github.common.vo.UserVO;
import com.admin.model.dto.DeptTree;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author wsp
 * @since 2018-01-20
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 查询部门树菜单
     *
     * @param userVO
     * @return 树
     */
    List<DeptTree> selectListTree(UserVO userVO);

    /**
     * 添加信息部门
     *
     * @param sysDept
     * @return
     */
    Res insertDept(SysDept sysDept, UserVO userVO);

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    Boolean deleteDeptById(Integer id,UserVO userVO);

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    Boolean updateDeptById(SysDept sysDept);


    /**
     * 获取当前用户的子部门信息
     *
     * @param userVO 用户信息
     * @return 子部门列表
     */
    List<Integer> getUserAllDepts(UserVO userVO) ;


}
