package cjj.demo.shiro.springboot.mybatis.config;

import cjj.demo.shiro.springboot.mybatis.dao.PermissionDao;
import cjj.demo.shiro.springboot.mybatis.dao.RoleDao;
import cjj.demo.shiro.springboot.mybatis.dao.UserDao;
import cjj.demo.shiro.springboot.mybatis.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 1.创建一个类继承AuthorizingRealm类（实现了Realm接口类）
 * 2.重写doGetAuthorizationInfo和doGetAuthenticationInfo方法
 * 3.重写getName方法返回当前realm的自定义名称
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;

    @Override
    public String getName() {
        return "myRealm";
    }

    /**
     * 获取授权数据(角色权限信息)
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户的用户名
        String username  = (String) principalCollection.iterator().next();
        //根据用户名查询用户角色
        Set<String> roles = roleDao.getRoleNamesByUsername(username);
        //根据用户名查询用户权限
        Set<String> permissions = permissionDao.getPermissionByUsername(username);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 获取认证的安全数据（从数据库查询到的用户正确数据）
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //参数authenticationToken就是传递的 subject.login(token)
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        //从token中获取用户名
        String username = token.getUsername();
        //根据用户名从数据库查询用户安全数据
        User user = userDao.getUserByUsername(username);
        AuthenticationInfo info=new SimpleAuthenticationInfo(username,user.getPassword(),getName());
        return info;
    }
}
