package com.admin.rest;

import com.admin.model.entity.SysUserRole;
import com.admin.service.SysRoleService;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.common.constant.CommonConstant;
import com.github.common.security.ZlPasswordEncoder;
import com.github.common.util.Query;
import com.github.common.util.Res;
import com.github.common.vo.UserVO;
import com.github.common.web.BaseController;
import com.admin.common.util.GeneratorUtils;
import com.admin.model.dto.UserDTO;
import com.admin.model.dto.UserInfo;
import com.admin.model.entity.SysUser;
import com.admin.service.SysUserService;
import com.admin.service.ValidateCodeService;
import com.xiaoleilu.hutool.io.FileUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wsp
 * @date 2017/10/28
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * 加密
     */
    private static final PasswordEncoder ENCODER = new ZlPasswordEncoder();

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private SysRoleService sysRoleService ;
    /**
     * 获取当前用户信息（角色、权限） 并且异步初始化用户部门信息
     *
     * @param userVo 当前用户信息
     * @return 用户名
     */
    @GetMapping("/info")
    public Res<UserInfo> user(UserVO userVo) {
        UserInfo userInfo = sysUserService.findUserInfo(userVo);
        return new Res<>(userInfo);
    }

    /**
     * 通过ID查询当前用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public UserVO user(@PathVariable String id) {
        return sysUserService.selectUserVoById(id);
    }

    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "path")
    @DeleteMapping("/{id}")
    public Res<Boolean> userDel(@PathVariable Integer id) {
        SysUser sysUser = sysUserService.selectById(id);
        return new Res<>(sysUserService.deleteUserById(sysUser));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @PostMapping
    public Res<Boolean> user(@RequestBody UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
        sysUser.setUserId(GeneratorUtils.uuid());
        sysUserService.insert(sysUser);

        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return new Res<>(Boolean.TRUE);
    }

    /**
     * 添加客户经理
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @PostMapping("addUserForCustomerManager")
    public Res<String> addUserForCustomerManager(@RequestBody UserDTO userDto) {
        String randomPassword = sysUserService.addUserForCustomerManager(userDto);
        return new Res<>(randomPassword);
    }

    /**
     * 修改客户经理手机号码
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @PostMapping("changePhone")
    public void  changePhoneByUserName(@RequestBody UserDTO userDto){
        sysUserService.changePhone(userDto);
    }





    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @PutMapping
    public Res<Boolean> userUpdate(@RequestBody UserDTO userDto) {
        SysUser user = sysUserService.selectById(userDto.getUserId());
        return new Res<>(sysUserService.updateUser(userDto, user.getUsername()));
    }

    /**
     * 通过用户名查询用户及其角色信息
     *
     * @param username 用户名
     * @return UseVo 对象
     */
    @GetMapping("/findUserByUsername/{username}")
    public UserVO findUserByUsername(@PathVariable String username) {
        return sysUserService.findUserByUsername(username);
    }

    /**
     * 通过手机号查询用户及其角色信息
     *
     * @param mobile 手机号
     * @return UseVo 对象
     */
    @GetMapping("/findUserByMobile/{mobile}")
    public UserVO findUserByMobile(@PathVariable String mobile) {
        return sysUserService.findUserByMobile(mobile);
    }

    /**
     * 通过OpenId查询
     *
     * @param openId openid
     * @return 对象
     */
    @GetMapping("/findUserByOpenId/{openId}")
    public UserVO findUserByOpenId(@PathVariable String openId) {
        return sysUserService.findUserByOpenId(openId);
    }

    /**
     * 分页查询用户
     *
     * @param params 参数集
     * @param userVO 用户信息
     * @return 用户集合
     */
    @RequestMapping("/userPage")
    public Page userPage(@RequestParam Map<String, Object> params, UserVO userVO) {
        return sysUserService.selectWithRolePage(new Query(params), userVO);
    }

    /**
     * 上传用户头像 (多机部署有问题，建议使用独立的文件服务器)
     *
     * @param file 资源
     * @return filename map
     */
    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
        String fileExt = FileUtil.extName(file.getOriginalFilename());
        Map<String, String> resultMap = new HashMap<>(1);
        return resultMap;
    }

    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @param userVo  登录用户信息
     * @return success/false
     */
    @PutMapping("/editInfo")
    public Res<Boolean> editInfo(@RequestBody UserDTO userDto, UserVO userVo) {
        return sysUserService.updateUserInfo(userDto, userVo);
    }


    /**
     * REST-通过sys_user主键查找username
     */
    @PostMapping("/selectUsernameById")
    public String selectUsernameById(@RequestParam("userId") String userId) {
        return sysUserService.selectUsernameById(userId);
    }

}
