package cjj.demo.tmpl.auth.shiro;

import cjj.demo.tmpl.auth.entity.Permission;
import cjj.demo.tmpl.auth.entity.Role;
import cjj.demo.tmpl.auth.service.IAdminService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义域 CustomRealm
 *
 * Realm即领域，相当于datasource数据源，securityManager进行安全认证需要通过Realm获取用户身份信息及用户权限数据
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IAdminService adminService;

    /**
     * 此方法必须有，否则Shiro报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 用户授权：为当前登录成功的用户授予权限 & 分配角色
     * @param principals
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("start shiro authorization-授权");
        String accessToken = (String) principals.getPrimaryPrincipal();
        // 授权器
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Long adminid = ((Integer)JWTUtil.parseToken(accessToken).getPayload().getClaim(JWTPayload.SUBJECT)).longValue();

        // 通过用户id获取该用户所拥有的角色名称
        List<Role> roleList = adminService.getRoleByAdminid(adminid);
        if(CollUtil.isNotEmpty(roleList)){
            authorizationInfo.addRoles(roleList.stream()
                    .filter(role -> StrUtil.isNotBlank(role.getRoleName()))
                    .map(Role::getRoleName)
                    .collect(Collectors.toList()));
        }

        // 通过用户id获取该用户所拥有的权限授权 如：sys:user:add
        List<Permission> permissionList = adminService.getPermissionByAdminid(adminid);
        if(CollUtil.isNotEmpty(permissionList)){
            authorizationInfo.addStringPermissions(permissionList.stream()
                    .filter(permission -> StrUtil.isNotBlank(permission.getPerms()))
                    .map(Permission::getPerms)
                    .collect(Collectors.toList()));
        }

        return authorizationInfo;
    }

    /**
     * 用户认证：验证当前登录的用户，获取认证信息
     *  以前是验证用户名/密码。现在我们验证 token，把我们的 token 交还给 认证器
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("start shiro authentication-认证");
        // 把我们的 token 交还给 认证器
        JwtToken jwtToken = (JwtToken) token;

        return new SimpleAuthenticationInfo(jwtToken.getPrincipal(), jwtToken.getCredentials(), CustomRealm.class.getName());
    }

}
