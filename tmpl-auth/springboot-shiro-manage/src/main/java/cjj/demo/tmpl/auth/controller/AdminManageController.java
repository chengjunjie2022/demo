package cjj.demo.tmpl.auth.controller;

import cjj.demo.tmpl.auth.dto.req.LoginReqVo;
import cjj.demo.tmpl.auth.dto.resp.LoginRespVo;
import cjj.demo.tmpl.auth.entity.Admin;
import cjj.demo.tmpl.auth.service.IAdminService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import road.cjj.commons.entity.R;
import road.cjj.commons.entity.RC;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Slf4j
@Api(tags = "管理员表管理")
@RestController
@RequestMapping("/manage/admin")
public class AdminManageController {

    @Resource
    private IAdminService adminService;

    /**
     * 登录
     * @param req
     * @return
     */
    @ApiOperation(value = "用户登录",notes = "用户登录接口")
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody @Valid LoginReqVo req){
        //1. 前端传参校验

        //2. 业务参数校验
        Admin admin = adminService.getAdminByLoginName(req.getLoginName());
        if(ObjectUtil.isEmpty(admin)){
            return R.err(RC.ERR_ACCOUNT_NO.getCode(), RC.ERR_ACCOUNT_NO.getMsg());
        }
        SecureUtil.hmacMd5()
        DigestUtil.md5

        if (!BCrypt.checkpw(loginReqVo.getPassword(),user.getPassword())){// 密码不正确
            throw new BusinessException(ResponseCode.ACCOUNT_PASSWORD_ERROR);
        }
        if (user.getStatus() == 2){// 账号锁定
            return Response.error(ResponseCode.ACCOUNT_LOCK.getMessage());
        }
        // 查询用户拥有的权限菜单列表
        List<PermissionRespNodeVo> permissionRespNodeVos = permissionService.permissionTreeListByUserId(user.getId());
        // 查询前端按钮权限
        List<String> permissionCodes = sysUserRoleDao.getPermissionCodesByUserId(user.getId());
        // 通过用户id获取该用户所拥有的角色名称
        List<String> roleNames = sysUserRoleDao.getRoleNameByUserId(user.getId());
        // 通过用户id获取该用户所拥有的权限授权 如：sys:user:add
        List<String> permissionPerms = sysUserRoleDao.getPermissionPermsByUserId(user.getId());
        /*List<String> roleNames = new ArrayList<>();
        roleNames.add("admin");
        List<String> permissionPerms = new ArrayList<>();
        permissionPerms.add("sys:user");
        permissionPerms.add("user:test");*/
        // 用户业务 token 令牌
        String accessToken = JwtTokenUtil.getInstance()
                .setIssuer(tokenConfig.getIssuer())
                .setSecret(tokenConfig.getSecretKey())
                .setExpired(tokenConfig.getAccessTokenExpireTime().toMillis())
                .setSubObject(user.getId())
                .setClaim(Constant.JWT_ROLES_KEY, JSON.toJSONString(roleNames))
                .setClaim(Constant.JWT_PERMISSIONS_KEY, JSON.toJSONString(permissionPerms))
                .setClaim(Constant.JWT_USER_NAME, user.getUsername())
                .generateToken();
        // 每次登录的时候吧token放到 redis，用于只能一个账号同时在线
        redisService.set(Constant.JWT_USER_NAME+user.getId(),accessToken);
        // 每次登录先删除需要重新登录的标记
        redisService.delete(Constant.JWT_USER_LOGIN_BLACKLIST+user.getId());
        LoginRespVo loginRespVo = new LoginRespVo();
        loginRespVo.setAuthorization(accessToken);
        loginRespVo.setId(user.getId());
        loginRespVo.setPhone(user.getPhone());
        loginRespVo.setUsername(user.getUsername());
        loginRespVo.setNickname(user.getNickName());
        loginRespVo.setMenus(permissionRespNodeVos);
        loginRespVo.setPermissions(permissionCodes);
        return Response.success(loginRespVo);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @ApiOperation(value = "用户登出",notes = "用户登出的接口")
    @GetMapping("/logout")
    public Response<String> logout(HttpServletRequest request){
        String accessToken = null;
        //String refreshToken = null;
        // 退出时，不管成功还是失败都要退出
        try {
            accessToken = request.getHeader(Constant.ACCESS_TOKEN);
            //refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
            return userService.logout(accessToken);
        } catch (Exception e) {
            logger.error("【logout error】，{}",e);
        }
        return Response.success();
    }

    @MyLog(title = "组织管理-用户管理",action = "分页查询用户接口")
    @RequiresPermissions("sys:user:list")
    @ApiOperation(value = "分页查询用户",notes = "分页查询用户接口")
    @PostMapping("/users")
    public Response<PageVo<UserTableRespVo>> pageInfo(@RequestBody @Valid UserPageReqVo userPageReqVo){
        return userService.pageInfo(userPageReqVo);
    }

    @ApiOperation(value = "新增用户",notes = "新增用户接口")
    @RequiresPermissions("sys:user:add")
    @MyLog(title = "组织管理-用户管理",action = "新增用户接口")
    @PostMapping("/user")
    public Response<String> addUser(@RequestBody @Valid UserAddReqVo userAddReqVo){
        return userService.addUser(userAddReqVo);
    }

    @MyLog(title = "组织管理-用户管理",action = "查询用户拥有的角色数据接口")
    @ApiOperation(value = "查询用户拥有的角色数据接口",notes = "查询用户拥有的角色数据接口")
    @GetMapping("/user/roles/{userId}")
    public Response<UserOwnRoleRespVo> getUserOwnRole(@PathVariable("userId")String userId){
        return userService.getUserOwnRole(userId);
    }

    @ApiOperation(value = "更新用户角色",notes = "保存用户拥有的角色信息接口")
    @RequiresPermissions("sys:user:role:update")
    @MyLog(title = "组织管理-用户管理",action = "保存用户拥有的角色信息接口")
    @PutMapping("/user/roles")
    public Response<String> saveUserOwnRole(@RequestBody @Valid UserOwnRoleReqVo vo){
        return userService.setUserOwnRole(vo);
    }

    @ApiOperation(value = "重新用户密码",notes = "重新用户密码接口")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "String",paramType = "form")
    @RequiresPermissions("sys:user:role:update")
    @MyLog(title = "组织管理-重置用户密码",action = "重置用户密码接口")
    @GetMapping("/user/password/{id}")
    public Response<String> resetUpdatePassword(@PathVariable("id") String userId){
        return userService.resetUpdatePassword(userId);
    }

    /*@MyLog(title = "组织管理-用户管理",action = "jwt token 刷新接口")
    @ApiOperation(value = "jwt token 刷新接口",notes = "jwt token 刷新接口")
    @GetMapping("/user/token")
    public Response<String> refreshToken(HttpServletRequest request){
        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
        return userService.refreshToken(refreshToken);
    }*/

    @ApiOperation(value = "更新用户",notes = "更新用户接口")
    @RequiresPermissions("sys:user:update")
    @MyLog(title = "组织管理-用户管理",action = "列表修改用户信息接口")
    @PutMapping("/user")
    public Response<String> updateUserInfo(@RequestBody @Valid UserUpdateReqVo userUpdateReqVo,HttpServletRequest request){
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String operationId = JwtTokenUtil.getInstance().getUserId(accessToken);// 操作人
        return userService.updateUserInfo(userUpdateReqVo,operationId);
    }

    @ApiOperation(value = "批量/删除用户",notes = "批量/删除用户接口")
    @RequiresPermissions("sys:user:delete")
    @MyLog(title = "组织管理-用户管理",action = "批量/删除用户接口")
    @DeleteMapping("/user")
    public Response<String> deletedUsers(@RequestBody @ApiParam(value = "用户id集合")List<String> list,HttpServletRequest request){
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String operationId = JwtTokenUtil.getInstance().getUserId(accessToken);// 操作人
        return userService.deletedUsers(list,operationId);
    }

    @ApiOperation(value = "用户信息详情",notes = "用户信息详情接口")
    @MyLog(title = "组织管理-用户管理",action = "用户信息详情接口")
    @GetMapping("/user/info")
    public Response<UserRespVo> detailInfo(HttpServletRequest request){
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = JwtTokenUtil.getInstance().getUserId(accessToken);
        return userService.detailInfo(userId);
    }

    @ApiOperation(value = "保存个人信息",notes = "保存个人信息接口")
    @MyLog(title = "组织管理-用户管理",action = "保存个人信息接口")
    @PutMapping("/user/info")
    public Response<String> saveUserInfo(@RequestBody UserUpdateDetailInfoReqVo updateDetailInfoReqVo, HttpServletRequest request){
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = JwtTokenUtil.getInstance().getUserId(accessToken);
        return userService.userUpdateDetailInfo(updateDetailInfoReqVo,userId);
    }

    @ApiOperation(value = "修改个人密码",notes = "修改个人密码接口")
    @MyLog(title = "组织管理-用户管理",action = "修改个人密码接口")
    @PutMapping("/user/password")
    public Response<String> updatePassword(@RequestBody @Valid UserUpdatePasswordReqVo userUpdatePasswordReqVo, HttpServletRequest request){
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        //String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
        return userService.userUpdatePassword(userUpdatePasswordReqVo,accessToken);
    }


}

