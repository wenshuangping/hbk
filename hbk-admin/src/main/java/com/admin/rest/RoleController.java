package com.admin.rest;

import com.admin.model.dto.RoleMenuDTO;
import com.admin.service.SysRoleMenuService;
import com.admin.service.SysRoleService;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.common.constant.enums.EnumDelFlag;
import com.github.common.vo.UserVO;
import com.admin.model.dto.RoleDTO;

import com.github.common.constant.CommonConstant;
import com.github.common.util.Query;
import com.github.common.util.Res;
import com.github.common.vo.SysRole;
import com.github.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wsp
 * @date 2017/11/5
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public SysRole role(@PathVariable Integer id) {
        return sysRoleService.selectById(id);
    }

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return success、false
     */
    @PostMapping
    public Res<Boolean> role(@RequestBody RoleDTO roleDto) {
        return new Res<>(sysRoleService.insertRole(roleDto));
    }

    /**
     * 修改角色
     *
     * @param roleDto 角色信息
     * @return success/false
     */
    @PutMapping
    public Res<Boolean> roleUpdate(@RequestBody RoleDTO roleDto) {
        return new Res<>(sysRoleService.updateRoleById(roleDto));
    }

    @DeleteMapping("/{id}")
    public Res<Boolean> roleDel(@PathVariable Integer id) {
        SysRole sysRole = sysRoleService.selectById(id);
        sysRole.setDelFlag(CommonConstant.STATUS_DEL);
        return new Res<>(sysRoleService.updateById(sysRole));
    }

    /**
     * 获取角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    @GetMapping("/roleList/{deptId}")
    public List<SysRole> roleList(@PathVariable Integer deptId) {
        return sysRoleService.selectListByDeptId(deptId);

    }

    /**
     * 分页查询角色信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @RequestMapping("/rolePage")
    public Page rolePage(@RequestParam Map<String, Object> params, UserVO userVO) {
        params.put(CommonConstant.DEL_FLAG, EnumDelFlag.UNDELETE.getValue());
        return sysRoleService.selectwithDeptPage(new Query(params), userVO);
    }

    /**
     * 更新角色菜单
     *
     * @param roleMenuDTO
     * @return success、false
     */
    @PostMapping("/roleMenuUpd")
    public Res<Boolean> roleMenuUpd(@RequestBody RoleMenuDTO roleMenuDTO) {
        SysRole sysRole = sysRoleService.selectById(roleMenuDTO.getRoleId());
        String[] menuIdsArr = roleMenuDTO.getMenuIds().split(",");
        return new Res<>(sysRoleMenuService.insertRoleMenus(sysRole.getRoleCode(), roleMenuDTO.getRoleId(), menuIdsArr));
    }
}
