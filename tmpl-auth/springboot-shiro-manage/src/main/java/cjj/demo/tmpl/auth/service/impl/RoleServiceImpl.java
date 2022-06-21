package cjj.demo.tmpl.auth.service.impl;

import cjj.demo.tmpl.auth.entity.Role;
import cjj.demo.tmpl.auth.mapper.RoleMapper;
import cjj.demo.tmpl.auth.service.IRoleService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import road.cjj.commons.entity.consts.NumE;
import road.cjj.commons.entity.params.PageP;
import road.cjj.commons.entity.params.TimeP;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
//    /**
//     * 角色管理分页数据
//     * @param rolePageReqVo
//     * @return
//     */
//    @Override
//    public Response<PageVo> pageInfoRoles(RolePageReqVo rolePageReqVo) {
//        PageHelper.startPage(rolePageReqVo.getPageNum(),rolePageReqVo.getPageSize());
//        List<SysRole> sysRoles = sysRoleDao.selectAll(rolePageReqVo);
//        return Response.success(PageUtil.getPageVo(new PageInfo<SysRole>(sysRoles)));
//    }
//
//    /**
//     * 新增角色
//     * @param roleReqVo
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Response<String> createRole(RoleReqVo roleReqVo) {
//        if (null == roleReqVo.getPermissionsIds() || roleReqVo.getPermissionsIds().isEmpty()){
//            throw new BusinessException(ResponseCode.DATA_ERROR);
//        }
//        String roleId = String.valueOf(idWorker.nextId());
//        // 角色入库
//        SysRole sysRole = SysRole.builder()
//                .id(roleId)
//                .name(roleReqVo.getName())
//                .status(roleReqVo.getStatus())
//                .description(roleReqVo.getDescription())
//                .createTime(new Date())
//                .build();
//        int addCount = sysRoleDao.insertSelective(sysRole);
//        if (addCount != 1){
//            throw new BusinessException(ResponseCode.OPERATION_ERROR);
//        }
//        // 添加 角色 权限关联表 数据
//        RolePermissionOperationReqVo rolePermissionOperationReqVo = new RolePermissionOperationReqVo();
//        rolePermissionOperationReqVo.setRoleId(roleId);
//        rolePermissionOperationReqVo.setPermissionIds(roleReqVo.getPermissionsIds());
//        rolePermissionService.addRolePermission(rolePermissionOperationReqVo);
//        return Response.success(ResponseCode.SUCCESS.getMessage());
//    }
//
//    /**
//     * 查询所有权限
//     * @return
//     */
//    @Override
//    public List<SysRole> selectAll() {
//        return sysRoleDao.selectAll(new RolePageReqVo());
//    }
//
//    /**
//     * 获取角色详情数据
//     * @param roleId
//     * @return
//     */
//    @Override
//    public Response<Set<String>> detailInfo(String roleId) {
//        if (StringUtils.isBlank(roleId)){
//            throw new BusinessException(ResponseCode.DATA_ERROR);
//        }
//        SysRole sysRole = sysRoleDao.selectByPrimaryKey(roleId);
//        if (sysRole == null){
//            logger.info("传入 的 id:{}不合法",roleId);
//            throw new BusinessException(ResponseCode.DATA_ERROR);
//        }
//        // 通过角色id查询拥有的权限ids
//        List<String> permissionIdsByRoleId = rolePermissionService.getPermissionIdsByRoleId(roleId);
//        Set<String> permissionIds = new HashSet<>(permissionIdsByRoleId);
//        return Response.success(permissionIds);
//    }
//
//    /**
//     * 更新角色信息
//     * @param roleUpdateReqVo
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Response<String> updateRole(RoleUpdateReqVo roleUpdateReqVo) {
//        SysRole sysRole = sysRoleDao.selectByPrimaryKey(roleUpdateReqVo.getId());
//        if (sysRole == null){
//            throw new BusinessException(ResponseCode.DATA_ERROR);
//        }
//        BeanUtils.copyProperties(roleUpdateReqVo,sysRole);
//        sysRole.setUpdateTime(new Date());
//        int updateCount = sysRoleDao.updateByPrimaryKeySelective(sysRole);
//        if (updateCount != 1){
//            throw new BusinessException(ResponseCode.OPERATION_ERROR);
//        }
//        // 更新 角色 权限 关联表
//        RolePermissionOperationReqVo rolePermissionOperationReqVo = new RolePermissionOperationReqVo();
//        rolePermissionOperationReqVo.setRoleId(roleUpdateReqVo.getId());
//        rolePermissionOperationReqVo.setPermissionIds(roleUpdateReqVo.getPermissionsIds());
//        rolePermissionService.addRolePermission(rolePermissionOperationReqVo);
//        // 标记关联用户主动去刷新
//        // 通过 角色id 查询所有的用户ids
//        List<String> userIdsByRoleId = userRoleService.getUserIdsByRoleId(roleUpdateReqVo.getId());
//        if (null != userIdsByRoleId && !userIdsByRoleId.isEmpty()){
//            for (String userId : userIdsByRoleId){
//                /**
//                 * 标记用户需要重新登录,禁止再访问我们的系统资源
//                 */
//                redisService.set(Constant.JWT_USER_LOGIN_BLACKLIST+userId,userId);
//                /**
//                 * 清楚用户授权数据缓存
//                 */
//                redisService.delete(Constant.IDENTIFY_CACHE_KEY+userId);
//            }
//        }
//        return Response.success(ResponseCode.SUCCESS.getMessage());
//    }
//
//    /**
//     * 更新角色状态
//     * @param roleId
//     * @param status
//     * @return
//     */
//    @Override
//    public Response<String> updateRoleStatus(String roleId, Integer status) {
//        if (StringUtils.isBlank(roleId)|| ObjectUtils.isEmpty(status)){
//            return Response.error(ResponseCode.DATA_ERROR.getMessage());
//        }
//        SysRole sysRole = new SysRole();
//        sysRole.setId(roleId);
//        sysRole.setStatus(status);
//        int result = sysRoleDao.updateByPrimaryKeySelective(sysRole);
//        if (result == 0){
//            return Response.error(ResponseCode.OPERATION_ERROR.getMessage());
//        }
//        return Response.success(ResponseCode.SUCCESS.getMessage());
//    }
//
//    /**
//     * 删除角色信息
//     * @param roleId
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Response<String> deletedRole(String roleId) {
//        // 1.
//        SysRole sysRole = new SysRole();
//        sysRole.setId(roleId);
//        sysRole.setDeleted(0);
//        sysRole.setUpdateTime(new Date());
//        int updateCount = sysRoleDao.updateByPrimaryKeySelective(sysRole);
//        if (updateCount != 1){
//            throw new BusinessException(ResponseCode.OPERATION_ERROR);
//        }
//        // 2.角色菜单权限关联数据删除，通过角色id删除权限id
//        rolePermissionService.removeByRoleId(roleId);
//        // 3.查询需要标记主动刷新的用户
//        List<String> userIdsByRoleId = userRoleService.getUserIdsByRoleId(roleId);
//        // 4.用户角色关联信息删除，通过角色id删除用户id
//        userRoleService.removeUseIdsrRoleId(roleId);
//        // 5.把跟该角色关联的用户标记起来，需要主动刷新token
//        if (userIdsByRoleId != null && !userIdsByRoleId.isEmpty()){
//            for (String userId : userIdsByRoleId){
//                /**
//                 * 标记用户需要重新登录,禁止再访问我们的系统资源
//                 */
//                redisService.set(Constant.JWT_USER_LOGIN_BLACKLIST+userId,userId);
//                /**
//                 * 清楚用户授权数据缓存
//                 */
//                redisService.delete(Constant.IDENTIFY_CACHE_KEY+userId);
//            }
//        }
//        return Response.success(ResponseCode.SUCCESS.getMessage());
//    }
}
