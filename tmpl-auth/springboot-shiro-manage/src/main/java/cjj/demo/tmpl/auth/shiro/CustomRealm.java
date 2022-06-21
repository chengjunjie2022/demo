package cjj.demo.tmpl.auth.shiro;

import cjj.demo.tmpl.auth.entity.Permission;
import cjj.demo.tmpl.auth.entity.Role;
import cjj.demo.tmpl.auth.service.IAdminService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import road.cjj.commons.redis.RedisUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义域 CustomRealm
 *
 * Realm即领域，相当于datasource数据源，securityManager进行安全认证需要通过Realm获取用户身份信息及用户权限数据
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IAdminService adminService;

    /**
     * 此方法必须有，不然我们自定义的 CustomUsernamePasswordToken 不生效
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomUsernamePasswordToken;
    }

    /**
     * 用户授权，设置用户所拥有的 角色/权限
     * @param principals
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String accessToken = (String) principals.getPrimaryPrincipal();
        // 授权器
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        JWT jwt = JWTUtil.parseToken(accessToken);
        JWTPayload payload = jwt.getPayload();
        Long adminid = (Long) payload.getClaim("adminid");
        // 通过用户id获取该用户所拥有的角色名称
        List<Role> roleList = adminService.getRoleByAdminid(adminid);
        List<String> roleNames = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(roleNames)){
            authorizationInfo.addRoles(roleNames);
        }
        // 通过用户id获取该用户所拥有的权限授权 如：sys:user:add
        List<Permission> permissionList = adminService.getPermissionByAdminid(adminid);
        List<String> permissionPerms = permissionList.stream().map(Permission::getPerms).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(permissionPerms)){
            authorizationInfo.addStringPermissions(permissionPerms);
        }
        return authorizationInfo;
    }

    /**
     * 用户认证，以前是验证用户名/密码。现在我们验证 token，把我们的 token 交还给 认证器
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 把我们的 token 交还给 认证器
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) token;
        return new SimpleAuthenticationInfo(customUsernamePasswordToken.getPrincipal(), customUsernamePasswordToken.getCredentials(), CustomRealm.class.getName());
    }
}
