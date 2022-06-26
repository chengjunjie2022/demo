package cjj.demo.tmpl.auth.service;

import cjj.demo.tmpl.auth.entity.Admin;
import cjj.demo.tmpl.auth.entity.Permission;
import cjj.demo.tmpl.auth.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import road.cjj.commons.entity.R;

import java.util.List;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 根据登录名查询用户
     * @param loginName 登录名
     * @return
     */
    Admin getAdminByLoginName(String loginName);

    /**
     * 根据登录人id，获取登录人角色
     * @param adminid
     * @return
     */
    List<Role> getRoleByAdminid(Long adminid);

    /**
     * 根据登录人id，获取登录人权限
     * @param adminid
     * @return
     */
    List<Permission> getPermissionByAdminid(Long adminid);
}
