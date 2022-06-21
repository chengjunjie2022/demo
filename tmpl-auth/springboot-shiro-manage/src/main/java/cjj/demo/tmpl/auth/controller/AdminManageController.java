package cjj.demo.tmpl.auth.controller;

import cjj.demo.tmpl.auth.config.JwtConfig;
import cjj.demo.tmpl.auth.dto.req.LoginReqVo;
import cjj.demo.tmpl.auth.dto.resp.LoginRespVo;
import cjj.demo.tmpl.auth.dto.resp.PermissionRespNodeVo;
import cjj.demo.tmpl.auth.entity.Admin;
import cjj.demo.tmpl.auth.entity.Permission;
import cjj.demo.tmpl.auth.entity.Role;
import cjj.demo.tmpl.auth.service.IAdminService;
import cjj.demo.tmpl.auth.service.IPermissionService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import road.cjj.commons.entity.R;
import road.cjj.commons.entity.RC;
import road.cjj.commons.redis.RedisUtil;
import road.cjj.commons.redis.consts.RedisConst;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IAdminService adminService;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IPermissionService permissionService;

    /**
     * 登录
     * @param req
     * @return
     */
    @ApiOperation(value = "用户登录",notes = "用户登录接口")
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody @Valid LoginReqVo req){
        //1. 参数校验
        //1.1 前端传参校验

        //1.2 业务参数校验
        Admin admin = adminService.getAdminByLoginName(req.getLoginName());
        if(ObjectUtil.isEmpty(admin)){
            return R.err(RC.ERR_ACCOUNT_NO.getCode(), RC.ERR_ACCOUNT_NO.getMsg());
        }
        if (!admin.getPwd().equals(req.getPwd())){// 密码不正确
            return R.err(RC.ERR_PWD.getCode(), RC.ERR_PWD.getMsg());
        }
        if (admin.getIsDel()){// 账号删除
            return R.err(RC.ERR_ACCOUNT_DELETED.getCode(), RC.ERR_ACCOUNT_DELETED.getMsg());
        }
        if (admin.getStat().intValue() == -1){// 账号锁定
            return R.err(RC.ERR_ACCOUNT_LOCK.getCode(), RC.ERR_ACCOUNT_LOCK.getMsg());
        }

        //2. 执行业务逻辑
        //2.1 查询
        // 查询用户拥有的权限菜单列表
        List<PermissionRespNodeVo> permissionRespNodeVos = permissionService.listTreePermissionByAdminid(admin.getId());

        // 通过用户id获取该用户所拥有的角色名称
        List<String> roleNames = ListUtil.empty();
        List<Role> roleList = adminService.getRoleByAdminid(admin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            roleNames = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
        }

        // 通过用户id获取该用户所拥有的权限授权 如：sys:user:add
        List<String> permissionPerms = ListUtil.empty();
        // 查询前端按钮权限
        List<String> permissionCodes = ListUtil.empty();
        List<Permission> permissionList = adminService.getPermissionByAdminid(admin.getId());
        if(CollUtil.isNotEmpty(permissionList)){
            permissionPerms = permissionList.stream().map(Permission::getPerms).collect(Collectors.toList());
            permissionCodes = permissionList.stream().map(Permission::getCode).collect(Collectors.toList());
        }


        //2.2 运算
        // 用户业务 token 令牌
        String accessToken = JWTUtil.createToken(MapUtil.builder(new HashMap<String, Object>())
                        .put(JWTPayload.ISSUER, jwtConfig.getIssuer())
                        .put(JWTPayload.ISSUED_AT, DateTime.now())//签发时间
                        .put(JWTPayload.EXPIRES_AT, DateTime.now().offset(DateField.HOUR, (int)jwtConfig.getAccessTokenExpireTime().toHours()))//过期时间
                        .put(JWTPayload.NOT_BEFORE, DateTime.now())//生效时间
                        .put(JWTPayload.SUBJECT, admin.getId())
                        .put(RedisConst.JWT_ROLES_KEY, JSONUtil.toJsonStr(roleNames))
                        .put(RedisConst.JWT_PERMISSIONS_KEY, JSONUtil.toJsonStr(permissionPerms))
                        .put(RedisConst.JWT_LOGIN_NAME, admin.getLoginName())
                .build(), jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));

        //2.3 存储
        // 每次登录的时候吧token放到 redis，用于只能一个账号同时在线
        redisUtil.set(RedisConst.JWT_LOGIN_NAME + admin.getId(), accessToken);
        // 每次登录先删除需要重新登录的标记
        redisUtil.delete(RedisConst.JWT_USER_LOGIN_BLACKLIST + admin.getId());

        //3. 返回结果
        LoginRespVo respVo = new LoginRespVo();
        BeanUtil.copyProperties(admin, respVo);
        respVo.setAuthorization(accessToken).setPermissions(permissionCodes).setMenus(permissionRespNodeVos);
        return R.suc(respVo);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @ApiOperation(value = "用户登出",notes = "用户登出的接口")
    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        String accessToken = request.getHeader(RedisConst.ACCESS_TOKEN);
        // shiro 退出
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            subject.logout();
        }

        // 获取用户 id
        JWT jwt = JWTUtil.parseToken(accessToken);
        JWTPayload payload = jwt.getPayload();
        Long adminid = ((Integer)payload.getClaim(JWTPayload.SUBJECT)).longValue();

        // 每次退出登录先删除需要重新登录的标记
        redisUtil.delete(RedisConst.JWT_USER_LOGIN_BLACKLIST + adminid);
        /**
         * 清楚用户授权数据缓存
         */
        redisUtil.delete(RedisConst.IDENTIFY_CACHE_KEY + adminid);
        return R.suc("success");
    }

    @RequiresPermissions("sys:admin:page")
    @ApiOperation(value = "分页查询用户",notes = "分页查询用户接口")
    @PostMapping("/page")
    public R<String> pageInfo(){
        return R.suc("page");
    }

    @ApiOperation(value = "新增用户",notes = "新增用户接口")
    @RequiresPermissions("sys:admin:save")
    @PostMapping("/save")
    public R<String> addUser(){
        return R.suc("save");
    }

//    @MyLog(title = "组织管理-用户管理",action = "分页查询用户接口")
//    @RequiresPermissions("sys:user:list")
//    @ApiOperation(value = "分页查询用户",notes = "分页查询用户接口")
//    @PostMapping("/users")
//    public Response<PageVo<UserTableRespVo>> pageInfo(@RequestBody @Valid UserPageReqVo userPageReqVo){
//        return userService.pageInfo(userPageReqVo);
//    }
//
//    @ApiOperation(value = "新增用户",notes = "新增用户接口")
//    @RequiresPermissions("sys:user:add")
//    @MyLog(title = "组织管理-用户管理",action = "新增用户接口")
//    @PostMapping("/user")
//    public Response<String> addUser(@RequestBody @Valid UserAddReqVo userAddReqVo){
//        return userService.addUser(userAddReqVo);
//    }
//
//    @MyLog(title = "组织管理-用户管理",action = "查询用户拥有的角色数据接口")
//    @ApiOperation(value = "查询用户拥有的角色数据接口",notes = "查询用户拥有的角色数据接口")
//    @GetMapping("/user/roles/{userId}")
//    public Response<UserOwnRoleRespVo> getUserOwnRole(@PathVariable("userId")String userId){
//        return userService.getUserOwnRole(userId);
//    }
//
//    @ApiOperation(value = "更新用户角色",notes = "保存用户拥有的角色信息接口")
//    @RequiresPermissions("sys:user:role:update")
//    @MyLog(title = "组织管理-用户管理",action = "保存用户拥有的角色信息接口")
//    @PutMapping("/user/roles")
//    public Response<String> saveUserOwnRole(@RequestBody @Valid UserOwnRoleReqVo vo){
//        return userService.setUserOwnRole(vo);
//    }
//
//    @ApiOperation(value = "重新用户密码",notes = "重新用户密码接口")
//    @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "String",paramType = "form")
//    @RequiresPermissions("sys:user:role:update")
//    @MyLog(title = "组织管理-重置用户密码",action = "重置用户密码接口")
//    @GetMapping("/user/password/{id}")
//    public Response<String> resetUpdatePassword(@PathVariable("id") String userId){
//        return userService.resetUpdatePassword(userId);
//    }
//
//    /*@MyLog(title = "组织管理-用户管理",action = "jwt token 刷新接口")
//    @ApiOperation(value = "jwt token 刷新接口",notes = "jwt token 刷新接口")
//    @GetMapping("/user/token")
//    public Response<String> refreshToken(HttpServletRequest request){
//        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
//        return userService.refreshToken(refreshToken);
//    }*/
//
//    @ApiOperation(value = "更新用户",notes = "更新用户接口")
//    @RequiresPermissions("sys:user:update")
//    @MyLog(title = "组织管理-用户管理",action = "列表修改用户信息接口")
//    @PutMapping("/user")
//    public Response<String> updateUserInfo(@RequestBody @Valid UserUpdateReqVo userUpdateReqVo,HttpServletRequest request){
//        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
//        String operationId = JwtTokenUtil.getInstance().getUserId(accessToken);// 操作人
//        return userService.updateUserInfo(userUpdateReqVo,operationId);
//    }
//
//    @ApiOperation(value = "批量/删除用户",notes = "批量/删除用户接口")
//    @RequiresPermissions("sys:user:delete")
//    @MyLog(title = "组织管理-用户管理",action = "批量/删除用户接口")
//    @DeleteMapping("/user")
//    public Response<String> deletedUsers(@RequestBody @ApiParam(value = "用户id集合")List<String> list,HttpServletRequest request){
//        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
//        String operationId = JwtTokenUtil.getInstance().getUserId(accessToken);// 操作人
//        return userService.deletedUsers(list,operationId);
//    }
//
//    @ApiOperation(value = "用户信息详情",notes = "用户信息详情接口")
//    @MyLog(title = "组织管理-用户管理",action = "用户信息详情接口")
//    @GetMapping("/user/info")
//    public Response<UserRespVo> detailInfo(HttpServletRequest request){
//        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
//        String userId = JwtTokenUtil.getInstance().getUserId(accessToken);
//        return userService.detailInfo(userId);
//    }
//
//    @ApiOperation(value = "保存个人信息",notes = "保存个人信息接口")
//    @MyLog(title = "组织管理-用户管理",action = "保存个人信息接口")
//    @PutMapping("/user/info")
//    public Response<String> saveUserInfo(@RequestBody UserUpdateDetailInfoReqVo updateDetailInfoReqVo, HttpServletRequest request){
//        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
//        String userId = JwtTokenUtil.getInstance().getUserId(accessToken);
//        return userService.userUpdateDetailInfo(updateDetailInfoReqVo,userId);
//    }
//
//    @ApiOperation(value = "修改个人密码",notes = "修改个人密码接口")
//    @MyLog(title = "组织管理-用户管理",action = "修改个人密码接口")
//    @PutMapping("/user/password")
//    public Response<String> updatePassword(@RequestBody @Valid UserUpdatePasswordReqVo userUpdatePasswordReqVo, HttpServletRequest request){
//        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
//        //String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
//        return userService.userUpdatePassword(userUpdatePasswordReqVo,accessToken);
//    }


}

