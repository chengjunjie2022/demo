package cjj.demo.tmpl.auth.mapper;

import cjj.demo.tmpl.auth.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
public interface RoleMapper extends BaseMapper<Role> {
    int insert(SysRole record);

    int insertSelective(SysRole record);

    /**
     * 角色管理分页数据查询
     * @param rolePageReqVo
     * @return
     */
    List<SysRole> selectAll(RolePageReqVo rolePageReqVo);

    /**
     * 根据主键 id 查询
     * @param roleId
     * @return
     */
    SysRole selectByPrimaryKey(String roleId);

    /**
     * 更新角色信息
     * @param sysRole
     * @return
     */
    int updateByPrimaryKeySelective(SysRole sysRole);
}
