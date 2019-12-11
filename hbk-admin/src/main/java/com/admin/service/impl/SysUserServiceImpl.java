package com.admin.service.impl;

import com.admin.common.util.GeneratorUtils;
import com.admin.constant.enums.EnumRole;
import com.admin.mapper.SysRoleMapper;
import com.admin.mapper.SysUserMapper;
import com.admin.mapper.SysUserRoleMapper;
import com.admin.model.dto.UserDTO;
import com.admin.model.dto.UserInfo;
import com.admin.model.entity.SysUser;
import com.admin.model.entity.SysUserRole;
import com.admin.service.SysDeptService;
import com.admin.service.SysMenuService;
import com.admin.service.SysUserRoleService;
import com.admin.service.SysUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.common.bean.interceptor.DataScope;
import com.github.common.constant.CommonConstant;
import com.github.common.security.ZlPasswordEncoder;
import com.github.common.util.Query;
import com.github.common.util.Res;
import com.github.common.vo.MenuVO;
import com.github.common.vo.SysRole;
import com.github.common.vo.UserVO;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wsp
 * @date 2017/10/31
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private static final PasswordEncoder ENCODER = new ZlPasswordEncoder();
    @Autowired
    private SysMenuService sysMenuService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysDeptService sysDeptService;



    @Override
    public UserInfo findUserInfo(UserVO userVo) {
        SysUser sysUser = baseMapper.selectById(userVo.getUserId());
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        // 设置角色列表
        List<String> roleCodeList = new ArrayList<>();
        if (userVo.getSysRole()!=null) {
                roleCodeList.add(userVo.getSysRole().getRoleCode());
        }
        String[] roles = roleCodeList.toArray(new String[roleCodeList.size()]);
        userInfo.setRoles(roles);

        // 设置权限列表（menu.permission）
        Set<MenuVO> menuVoSet = new HashSet<>();
        for (String role : roles) {
            List<MenuVO> menuVos = sysMenuService.findMenuByRoleName(role);
            menuVoSet.addAll(menuVos);
        }
        Set<String> permissions = new HashSet<>();
        for (MenuVO menuVo : menuVoSet) {
            if (StringUtils.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        userInfo.setPermissions(permissions.toArray(new String[permissions.size()]));
        return userInfo;
    }

    @Override
    public UserVO findUserByUsername(String username) {
        return sysUserMapper.selectUserVoByUsername(username);
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    public UserVO findUserByMobile(String mobile) {
        return  sysUserMapper.selectUserVoByMobile(mobile);
    }

    /**
     * 通过openId查询用户
     *
     * @param openId openId
     * @return 用户信息
     */
    @Override
    public UserVO findUserByOpenId(String openId) {
        return sysUserMapper.selectUserVoByOpenId(openId);
    }

    @Override
    public Page selectWithRolePage(Query query, UserVO userVO) {
        DataScope dataScope = null;
        Object username = query.getCondition().get("username");
        if(!userVO.getSysRole().getRoleCode().equals(EnumRole.ROLE_ADMIN.getValue())){
            dataScope = new DataScope();
            dataScope.setScopeName("deptId");
            dataScope.setIsOnly(true);
            dataScope.setDeptIds(sysDeptService.getUserAllDepts(userVO));
        }

        query.setRecords(sysUserMapper.selectUserVoPageDataScope(query, username, dataScope));
        return query;
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO selectUserVoById(String id) {
        return sysUserMapper.selectUserVoById(id);
    }



    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    public Boolean deleteUserById(SysUser sysUser) {
        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        this.deleteById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    @Override
    public Res<Boolean> updateUserInfo(UserDTO userDto, UserVO userVO) {
        SysUser sysUser = this.selectById(userVO.getUserId());
        if (StrUtil.isNotBlank(userDto.getPassword()) && StrUtil.isNotBlank(userDto.getNewpassword1())) {
            sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
        }
        sysUser.setPhone(userDto.getPhone());
        sysUser.setAvatar(userDto.getAvatar());
        sysUser.updateById();
        return new Res<>(this.updateById(sysUser));
    }

    @Override
    public Boolean updateUser(UserDTO userDto, String username) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(new Date());
        this.updateById(sysUser);

        SysUserRole condition = new SysUserRole();
        condition.setUserId(userDto.getUserId());
        sysUserRoleService.delete(new EntityWrapper<>(condition));
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }

    /**
     * 用户（mika用户,客户经理|商户）注册
     *
     * @param userDTO
     * @return
     */
    @Override
    public Res<Object> register(UserDTO userDTO) {

        Res<Object> res = new Res<>();

        //赋值ID
        userDTO.setUserId(GeneratorUtils.uuid());

        //查找对应角色数据
        String areaId = userDTO.getAreaId();
        List<SysRole> sysRoles = sysRoleMapper.selectList(new EntityWrapper<SysRole>().eq("area_id", areaId).and().eq("role_source", "0"));
        if (sysRoles.size() == 0) {
            res.setCode(2);
            res.setMsg("角色不存在!");
        }
        SysRole sysRole = sysRoles.get(0);
        //用户关联角色
        SysUserRole sysUserRole = new SysUserRole();
        //用户id
        sysUserRole.setUserId(userDTO.getUserId());
        //角色id
        sysUserRole.setRoleId(sysRole.getRoleId());
        //持久化
        sysUserRoleMapper.insert(sysUserRole);

        //持久化数据
        userDTO.setCreateTime(new Date());
        userDTO.setUpdateTime(new Date());
        if (!this.insert(userDTO)) {
            //操作失败
            res.setCode(1);
            return res;
        }

        //操作成功
        res.setCode(0);
        return res;
    }

    @Override
    public void changePhone(UserDTO userDTO) {
       sysUserMapper.updatePhoneByUserName(userDTO);
    }

    /**
     * 将Controller方法抽取到Service
     * @param userDto
     * @return
     */
    @Override
    public String addUserForCustomerManager(UserDTO userDto) {
        String randomPassword = ((int) ((Math.random() * 9 + 1) * 100000)) + "";

        // 添加客户经理
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(randomPassword));
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUserMapper.insert(sysUser);

        // 查询角色
        SysRole query = new SysRole();
        query.setRoleCode("customer_manager_r");
        SysRole sysRole = sysRoleMapper.selectOne(query);

        // 插入角色
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(sysUser.getUserId());
        userRole.setRoleId(sysRole.getRoleId());
        userRole.insert();
        return randomPassword;
    }

    /**
     * REST-通过sys_user主键查找username
     * @param userId
     */
    @Override
    public String selectUsernameById(String userId) {
        return sysUserMapper.selectUsernameById(userId);
    }

}
