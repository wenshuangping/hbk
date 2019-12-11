package com.admin.service.impl;

import com.admin.constant.enums.EnumDept;
import com.admin.constant.enums.EnumRole;
import com.admin.mapper.SysDeptMapper;
import com.admin.model.dto.DeptTree;
import com.admin.model.dto.TreeNode;
import com.admin.model.entity.SysDept;
import com.admin.service.SysDeptService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.common.constant.enums.EnumDelFlag;
import com.github.common.util.Res;
import com.github.common.vo.UserVO;
import com.admin.common.util.TreeUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 部门|公司管理 服务实现类
 * </p>
 *
 * @author wsp
 * @since 2018-01-20
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    /**
     * 添加信息部门
     * @param dept 部门
     * @param userVO 当前用户
     * @return
     */
    @Override
    public Res insertDept(SysDept dept, UserVO userVO) {
        Res<Integer> res = new Res<>();
        if( dept.getParentId()!=0) {
            SysDept parentDept = this.selectById(dept.getParentId());
            if (EnumDept.COMPANY.getValue().equals(parentDept.getType())) {
                res.setCode(EnumDept.ERROR_ADD_3.getValue());
            }
        }
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dept, sysDept);
        sysDept.setCreateTime(new Date());
        //sysDept.setCreateBy(userVO.getUserId());
        sysDept.setUpdateTime(new Date());
       // sysDept.setUpdateBy(userVO.getUserId());
        this.insert(sysDept);
        return res;
    }


    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    @Override
    public Boolean deleteDeptById(Integer id,UserVO userVO) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(id);
        sysDept.setUpdateTime(new Date());
        sysDept.setUpdateBy(userVO.getUserId());
        sysDept.setDelFlag(EnumDelFlag.DELETE.getValue());
        this.updateDeptById(sysDept);
        return Boolean.TRUE;
    }

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    @Override
    public Boolean updateDeptById(SysDept sysDept) {
        //更新部门状态
        this.updateById(sysDept);
        return Boolean.TRUE;
    }

    /**
     * 查询部门树
     *
     * @return 树
     */
    @Override
    public List<DeptTree> selectListTree(UserVO userVO) {
        SysDept condition = new SysDept();
        condition.setDelFlag(EnumDelFlag.UNDELETE.getValue());
        EntityWrapper<SysDept> sysDeptEntityWrapper = new EntityWrapper<>(condition);

        return getDeptTree(this.selectList(sysDeptEntityWrapper), 0,userVO);
    }

    /**
     * 构建部门树
     *
     * @param depts 部门
     * @param root  根节点
     * @return
     */
    private List<DeptTree> getDeptTree(List<SysDept> depts, int root,UserVO userVO) {
        List<DeptTree> trees = new ArrayList<>();

        Map<Integer,DeptTree> deptTreeMap =new HashMap<>();

        DeptTree node;
        for (SysDept dept : depts) {
            if (dept.getParentId().equals(dept.getDeptId())) {
                continue;
            }
            node = new DeptTree();
            node.setId(dept.getDeptId());
            node.setParentId(dept.getParentId());
            node.setName(dept.getName());
            node.setType(dept.getType());
            node.setAreaId(dept.getAreaId());
            trees.add(node);

            deptTreeMap.put( node.getId(),node);
        }

        List<DeptTree> rList =null;
        if (EnumRole.ROLE_ADMIN.getValue().equals(userVO.getSysRole().getRoleCode()) ){
            //超级管理员
            rList= TreeUtil.bulid(trees, root);
        }else {
            TreeUtil.bulid(trees, root);
            //普通用户
           rList = new LinkedList<>();
           DeptTree deptTree=deptTreeMap.get(userVO.getSysRole().getDeptId());
           rList.add(deptTreeMap.get(userVO.getSysRole().getDeptId()));
        }
        return rList;
    }

    /**
     * 获取当前用户的子部门信息
     *
     * @param userVO 用户信息
     * @return 子部门列表
     */
    @Override
    public  List<Integer> getUserAllDepts(UserVO userVO) {
        List<DeptTree> deptTreeList=  this.selectListTree(userVO);
        // 获取当前部门的子部门
        List<Integer> deptIds = new ArrayList<>();
        findDeptIds(deptIds,deptTreeList);

        return deptIds;
    }


    /***
     * 查找部门id
     * @param rlist
     * @param deptTreeList
     */
    private void findDeptIds(List<Integer> rlist, List<DeptTree> deptTreeList){
        for(DeptTree  deptTree  : deptTreeList){
            rlist.add(deptTree.getId());
            if(deptTree.getChildren()!=null && deptTree.getChildren().size()>0){
                findChildrenDeptIds(rlist,deptTree.getChildren());
            }
        }
    }

    /**
     * 子部门id
     * @param rlist
     * @param deptTreeList
     */
    private void findChildrenDeptIds(List<Integer> rlist, List<TreeNode> deptTreeList){
        for(TreeNode  treeNode  : deptTreeList){
            rlist.add(treeNode.getId());
            if(treeNode.getChildren()!=null && treeNode.getChildren().size()>0){
                findChildrenDeptIds(rlist,treeNode.getChildren());
            }
        }
    }



}
