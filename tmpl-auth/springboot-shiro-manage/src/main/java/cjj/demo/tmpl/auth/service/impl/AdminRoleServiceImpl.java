package cjj.demo.tmpl.auth.service.impl;

import cjj.demo.tmpl.auth.entity.AdminRole;
import cjj.demo.tmpl.auth.mapper.AdminRoleMapper;
import cjj.demo.tmpl.auth.service.IAdminRoleService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 管理员角色对应表 服务实现类
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Slf4j
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {

//    /**
//     * 根据用户id 查询用户拥有的角色数据接口
//     * @param userId
//     */
//    @Override
//    public List<String> getRoleIdsByUserId(String userId) {
//        return sysUserRoleDao.getRoleIdsByUserId(userId);
//    }
//
//    /**
//     * 更新用户角色
//     * @param userOwnRoleReqVo
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void addUserRoleInfo(UserOwnRoleReqVo userOwnRoleReqVo) {
//        if (userOwnRoleReqVo.getUserId() == null){
//            throw new BusinessException(ResponseCode.DATA_ERROR);
//        }
//        // 先删除用户原来的拥有的角色id
//        sysUserRoleDao.removeRoleIdsByUserId(userOwnRoleReqVo.getUserId());
//        if (userOwnRoleReqVo.getRoleIds().isEmpty()){// 为空表示去除该用户所有角色
//            return;
//        }
//        // 批量插入用户角色数据
//        List<SysUserRole> list = new ArrayList<>();
//        for (String roleId : userOwnRoleReqVo.getRoleIds()){
//            SysUserRole sysUserRole = new SysUserRole();
//            sysUserRole.setId(String.valueOf(idWorker.nextId()));
//            sysUserRole.setUserId(userOwnRoleReqVo.getUserId());
//            sysUserRole.setRoleId(roleId);
//            sysUserRole.setCreateTime(new Date());
//            list.add(sysUserRole);
//        }
//        int result = sysUserRoleDao.batchInsertUserRole(list);
//        if (result == 0){
//            throw new BusinessException(ResponseCode.OPERATION_ERROR);
//        }
//    }
//
//    /**
//     * 通过角色 id 集合查询所有的用户 id
//     * @param roleIdsByPermissionId
//     * @return
//     */
//    @Override
//    public List<String> getUserIdsByRoleIds(List<String> roleIdsByPermissionId) {
//        return sysUserRoleDao.getUserIdsByRoleIds(roleIdsByPermissionId);
//    }
//
//    /**
//     * 通过 单个角色id 查询所有的用户ids
//     * @param roleId
//     * @return
//     */
//    @Override
//    public List<String> getUserIdsByRoleId(String roleId) {
//        return sysUserRoleDao.getUserIdsByRoleId(roleId);
//    }
//
//    /**
//     * 通过角色id删除用户id
//     * @param roleId
//     * @return
//     */
//    @Override
//    public int removeUseIdsrRoleId(String roleId) {
//        return sysUserRoleDao.removeUseIdsrRoleId(roleId);
//    }
}
