package cjj.demo.tmpl.auth.shiro;

import cn.hutool.core.map.MapUtil;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    /**
     * 注入自定义认证器
     * @return
     */
    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher(){
        return new CustomHashedCredentialsMatcher();
    }

    /**
     * 注入自定义域
     * @return
     */
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();
        // 注入认证器 CustomHashedCredentialsMatcher
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        return customRealm;
    }

    /**
     * 安全管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // securityManager要完成校验，需要realm
        defaultWebSecurityManager.setRealm(customRealm());
        return defaultWebSecurityManager;
    }

    /**
     * shiro过滤器，配置拦截哪些请求
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 注入管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //自定义拦截器限制并发人数
        shiroFilterFactoryBean.setFilters(MapUtil.builder(new LinkedHashMap<String, Filter>())
                .put("token", new CustomAccessControllerFilter())
                .build());

        /**
         * 设置shiro的拦截规则，顺序判断
         *         anon 匿名用户可访问
         *         authc  认证用户可访问
         *         user 使用RemeberMe的用户可访问
         *         perms  对应权限可访问
         *         role  对应的角色可访问
         */
        shiroFilterFactoryBean.setFilterChainDefinitionMap(MapUtil.builder(new LinkedHashMap<String, String>())
                        .put("/images/**", "anon")
                        .put("/js/**", "anon")
                        .put("/css/**", "anon")
                        .put("/favicon.ico", "anon")
                        .put("/swagger-ui/**", "anon")
                        .put("/druid/**", "anon")
                        .put("/manage/admin/login", "anon")
                        .put("/manage/admin/token", "anon")
                        .put("/**","token,authc")
                .build());

        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/manage/admin/login");
        //// 设置成功之后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/success");
        //// 设置未授权界面，权限认证失败会访问该 URL
        //shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        return shiroFilterFactoryBean;
    }
}
