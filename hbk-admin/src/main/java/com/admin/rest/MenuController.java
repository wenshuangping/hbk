
package com.admin.rest;

import com.admin.model.entity.SysMenu;
import com.admin.service.SysMenuService;
import com.admin.service.SysUserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.common.vo.UserVO;
import com.admin.common.util.TreeUtil;
import com.admin.constant.enums.EnumRole;
import com.admin.model.dto.MenuTree;
import com.github.common.constant.CommonConstant;
import com.github.common.util.Res;
import com.github.common.vo.MenuVO;
import com.github.common.web.BaseController;
import com.xiaoleilu.hutool.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wsp
 * @date 2017/10/31
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 通过角色名称查询用户菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    @GetMapping("/findMenuByRole/{role}")
    public List<MenuVO> findMenuByRole(@PathVariable String role) {
        return sysMenuService.findMenuByRoleName(role);
    }

    /**
     * 返回当前用户的树形菜单集合
     *
     * @return 当前用户的树形菜单
     */
    @GetMapping(value = "/userMenu")
    public List<MenuTree> userMenu(UserVO userVO) throws Exception {

        // 获取符合条件得菜单
        Set<MenuVO> all = new HashSet<>();

        all.addAll(sysMenuService.findMenuByRoleName(userVO.getSysRole().getRoleCode()));
        List<MenuTree> menuTreeList = new ArrayList<>();
        all.forEach(menuVo -> {
            if (CommonConstant.MENU.equals(menuVo.getType())) {
                menuTreeList.add(new MenuTree( menuVo ));
            }
        });

        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        List<MenuTree> menuTreeListRes=  TreeUtil.bulid(menuTreeList, -1);
        return menuTreeListRes;
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/allTree")
    public List<MenuTree> getTree(UserVO userVO) {

        List<MenuTree> resultList=null;

        if(EnumRole.ROLE_ADMIN.getValue().equals(userVO.getSysRole().getRoleCode())){
            SysMenu condition = new SysMenu();
            condition.setDelFlag(CommonConstant.STATUS_NORMAL);
            resultList= TreeUtil.bulidTree(sysMenuService.selectList(new EntityWrapper<>(condition)), -1);
        }else{

            // 获取符合条件得菜单
            Set<MenuVO> all = new HashSet<>();

            all.addAll(sysMenuService.findMenuByRoleName(userVO.getSysRole().getRoleCode()));
            List<MenuTree> menuTreeList = new ArrayList<>();
            all.forEach(menuVo -> {
                    menuTreeList.add(new MenuTree( menuVo ));
            });
           // CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
            resultList=  TreeUtil.bulid(menuTreeList, -1);
        }
        return resultList;

    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleName 角色名称
     * @return 属性集合
     */
    @GetMapping("/roleTree/{roleName}")
    public List<Integer> roleTree(@PathVariable String roleName) {
        List<MenuVO> menus = sysMenuService.findMenuByRoleName(roleName);
        List<Integer> menuList = new ArrayList<>();
        for (MenuVO menuVo : menus) {
            menuList.add(menuVo.getMenuId());
        }
        return menuList;
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    public SysMenu menu(@PathVariable Integer id) {
        return sysMenuService.selectById(id);
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return success/false
     */
    @PostMapping
    public Res<Boolean> menu(@RequestBody SysMenu sysMenu)
    {
        logger.info("MenuController.menu :"+JSON.toJSONString(sysMenu));
        return new Res<>(sysMenuService.insert(sysMenu));
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     * TODO 级联删除下级节点
     */
    @DeleteMapping("/{id}")
    public Res<Boolean> menuDel(@PathVariable Integer id) {
        return new Res<>(sysMenuService.deleteMenu(id));
    }

    @PutMapping
    public Res<Boolean> menuUpdate(@RequestBody SysMenu sysMenu) {
        return new Res<>(sysMenuService.updateMenuById(sysMenu));
    }

}


