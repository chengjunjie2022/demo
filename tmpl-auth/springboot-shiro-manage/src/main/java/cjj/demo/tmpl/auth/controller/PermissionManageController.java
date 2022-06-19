package cjj.demo.tmpl.auth.controller;

import cjj.demo.tmpl.auth.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import road.cjj.commons.entity.consts.DefCfg;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Slf4j
@Api(tags = "权限表管理")
@RestController
@RequestMapping("/manage/permission")
public class PermissionManageController {

    @Resource
    private IPermissionService permissionService;

    @ApiOperation(value = "获取所有权限（包括按钮）",notes = "角色添加/编辑/分配权限时用到的结果数据")
    @RequiresPermissions(value = {"sys:role:update","sys:role:add"},logical = Logical.OR)
    @MyLog(title = "组织管理-菜单权限管理",action = "获取所有的菜单权限数据接口")
    @GetMapping("/permissions/tree/all")
    public Response getAllPermissionTree(){
        return Response.success(permissionService.selectAllTree());
    }

    @ApiOperation(value = "菜单权限表格展示-获取所有的菜单权限数据接口",notes = "菜单权限接口")
    @RequiresPermissions("sys:permission:list")
    @MyLog(title = "组织管理-菜单权限管理",action = "只递归查询到菜单结构数据接口")
    @GetMapping("/permissions")
    public Response getAllPermission(){
        return Response.success(permissionService.selectAll());
    }

    @ApiOperation(value = "菜单权限树",notes = "只递归查询目录与菜单，不要按钮，添加/编辑-权限的上级选择权限树结构展示")
    @MyLog(title = "组织管理-菜单权限管理",action = "新增菜单权限接口")
    @RequiresPermissions(value = {"sys:permission:update","sys:permission:add"},logical = Logical.OR)
    @GetMapping("/permissions/tree")
    public Response<List<PermissionRespNodeTreeVo>> getAllPermissionTreeExBtn(){
        return permissionService.selectAllMenuByTree();
    }

    @ApiOperation(value = "新增权限",notes = "新增菜单权限接口")
    @RequiresPermissions("sys:permission:add")
    @MyLog(title = "组织管理-菜单权限管理",action = "新增菜单权限接口")
    @PostMapping("/permission")
    public Response<String> createPermission(@RequestBody @Valid PermissionAddReqVo permissionAddReqVO){
        return permissionService.addPermission(permissionAddReqVO);
    }

    @ApiOperation(value = "更新权限",notes = "新更新菜单权限接口")
    @RequiresPermissions("sys:permission:update")
    @MyLog(title = "组织管理-菜单权限管理",action = "编辑菜单权限接口")
    @PutMapping("/permission")
    public Response<String> updatePermission(@RequestBody @Valid PermissionUpdateReqVo permissionUpdateReqVo){
        return permissionService.updatePermission(permissionUpdateReqVo);
    }

    @ApiOperation(value = "删除权限",notes = "删除菜单权限接口")
    @RequiresPermissions("sys:permission:delete")
    @MyLog(title = "组织管理-菜单权限管理",action = "删除菜单权限接口")
    @DeleteMapping("/permission/{permissionId}")
    public Response<String> deletedPermission(@PathVariable("permissionId") String permissionId){
        return permissionService.deletedPermission(permissionId);
    }
}

