package com.admin.rest;

import com.admin.model.entity.SysDept;
import com.admin.service.SysDeptService;
import com.github.common.util.Res;
import com.github.common.vo.UserVO;
import com.github.common.web.BaseController;
import com.admin.model.dto.DeptTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author wsp
 * @since 2018-01-20
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {


    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @GetMapping("/{id}")
    public SysDept get(@PathVariable Integer id) {
        return sysDeptService.selectById(id);
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public List<DeptTree> getTree(UserVO userVO) {
        return sysDeptService.selectListTree(userVO);
    }

    /**
     * 添加
     * @param sysDept 实体
     * @return success/false
     */
    @PostMapping("add")
    public Res add(@RequestBody SysDept sysDept, UserVO userVO) {
        return sysDeptService.insertDept(sysDept,userVO);
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id,UserVO userVO) {
        return sysDeptService.deleteDeptById(id,userVO);
    }

    /**
     * 编辑
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PutMapping
    public Boolean edit(@RequestBody SysDept sysDept,UserVO userVO) {
        sysDept.setUpdateTime(new Date());
        sysDept.setUpdateBy(userVO.getUserId());
        return sysDeptService.updateDeptById(sysDept);
    }
}
