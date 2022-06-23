package cjj.demo.tmpl.auth.service;

import cjj.demo.tmpl.auth.dto.resp.PermissionRespNodeVo;
import cjj.demo.tmpl.auth.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 根据管理员id获取权限树
     * @param adminid
     * @return
     */
    List<PermissionRespNodeVo> listTreePermissionByAdminid(Long adminid);
}
