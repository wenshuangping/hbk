package com.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.admin.mapper.SysRoleMenuMapper;
import com.admin.model.entity.SysRoleMenu;
import com.admin.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author wsp
 * @since 2017-10-29
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Override
    public Boolean insertRoleMenus(String role, Integer roleId, String[] menuIds) {
        SysRoleMenu condition = new SysRoleMenu();
        condition.setRoleId(roleId);
        this.delete(new EntityWrapper<>(condition));

        List<SysRoleMenu> roleMenuList = new ArrayList<>();
        for (String menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(new Integer(menuId));
            roleMenuList.add(roleMenu);
        }
        return this.insertBatch(roleMenuList);
    }
}
