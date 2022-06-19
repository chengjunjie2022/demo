package cjj.demo.tmpl.auth.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import road.cjj.commons.entity.R;
import road.cjj.commons.entity.consts.DefCfg;
import road.cjj.commons.entity.consts.NumE;
import road.cjj.commons.entity.params.PageP;
import road.cjj.commons.entity.params.TimeP;
import road.cjj.commons.util.H;
import road.cjj.service.common.base.entity.Role;
import road.cjj.service.common.biz.service.IRoleService;
import road.cjj.web.common.dto.req.RoleInMgrReq;
import road.cjj.web.common.dto.req.RoleQryMgrReq;
import road.cjj.web.common.dto.resp.RoleOutMgrReq;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Slf4j
@Api(tags = "角色表管理")
@RestController
@RequestMapping("/manage/role")
public class RoleManageController {

    @Resource
    private IRoleService roleService;

    @ApiOperation(value = "分页获取角色数据接口",notes = "分页获取角色数据接口")
    @RequiresPermissions("sys:role:list")
    @MyLog(title = "组织管理-角色管理",action = "分页获取角色数据接口")
    @PostMapping("/roles")
    public Response<PageVo> pageInfo(@RequestBody RolePageReqVo rolePageReqVo){
        return roleService.pageInfoRoles(rolePageReqVo);
    }

    @ApiOperation(value = "新增角色接口",notes = "新增角色")
    @RequiresPermissions("sys:role:add")
    @MyLog(title = "组织管理-角色管理",action = "新增角色接口")
    @PostMapping("/role")
    public Response<String> createRole(@RequestBody @Valid RoleReqVo roleReqVo){
        return roleService.createRole(roleReqVo);
    }

    @ApiOperation(value = "获取角色详情接口",notes = "获取角色详情接口")
    @RequiresPermissions("sys:role:detail")
    @MyLog(title = "组织管理-角色管理",action = "获取角色详情接口")
    @GetMapping("/role/{id}")
    public Response<Set<String>> detailInfo(@PathVariable("id") String roleId){
        return roleService.detailInfo(roleId);
    }

    @ApiOperation(value = "更新角色信息",notes = "更新角色信息接口")
    @RequiresPermissions("sys:role:update")
    @MyLog(title = "组织管理-角色管理",action = "更新角色信息接口")
    @PutMapping("/role")
    public Response<String> updateRole(@RequestBody @Valid RoleUpdateReqVo roleUpdateReqVo){
        return roleService.updateRole(roleUpdateReqVo);
    }

    @ApiOperation(value = "更新角色状态",notes = "更新角色状态接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "String",paramType = "form"),
            @ApiImplicitParam(name = "status",value = "状态status",required = true,dataType = "Integer",paramType = "form"),
    })
    @RequiresPermissions("sys:role:update:status")
    @MyLog(title = "组织管理-角色管理",action = "更新角色状态接口")
    @PostMapping("/role/{id}/{status}")
    public Response<String> updateRoleStatus(@PathVariable("id")String roleId,@PathVariable("status") Integer status){
        return roleService.updateRoleStatus(roleId,status);
    }

    @ApiOperation(value = "删除角色信息",notes = "删除角色接口")
    @RequiresPermissions("sys:role:delete")
    @MyLog(title = "组织管理-角色管理",action = "删除角色接口")
    @DeleteMapping("/role/{id}")
    public Response<String> deletedRole(@PathVariable("id") String roleId){
        return roleService.deletedRole(roleId);
    }
}

