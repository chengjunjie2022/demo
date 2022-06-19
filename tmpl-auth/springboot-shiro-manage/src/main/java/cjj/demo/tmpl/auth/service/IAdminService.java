package cjj.demo.tmpl.auth.service;

import cjj.demo.tmpl.auth.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import road.cjj.commons.entity.R;

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
     * 登陆
     * @param loginReqVo
     * @return
     */
    R<LoginRespVo> login(Admin admin);
}
