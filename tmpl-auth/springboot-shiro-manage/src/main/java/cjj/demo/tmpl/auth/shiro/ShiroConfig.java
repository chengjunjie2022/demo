package cjj.demo.tmpl.auth.shiro;

import cn.hutool.core.map.MapUtil;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @Configuration注解的类会优先于其他@Component或@Service注解的类创建，
 *  也就是说，当@Configuration类创建时需要自动注入shiroService时，其实此时被@Service修饰的ShiroService还没有被创建呢，所以自动注入的就是null，导致注入失败。
 *  解决方案就是使用方法参数的方式注入
 */
@Configuration
public class ShiroConfig {

    /**
     * 缓存管理器
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(){
        return new RedisCacheManager();
    }

    /**
     * 注入自定义认证器
     * @return
     */
    @Bean
    public CustomMatcher customMatcher(){
        return new CustomMatcher();
    }

    /**
     * 注入自定义域
     * @return
     */
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();
        // 注入认证器
        customRealm.setCredentialsMatcher(customMatcher());
        // 设置缓存管理器
        customRealm.setCacheManager(redisCacheManager());

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
                .put("token", new AuthFilter())
                .build());

        /**
         * 设置shiro的拦截规则，顺序判断
         *         anon 开方权限，匿名用户/游客，可以直接访问
         *         authc  认证用户可访问
         *         user 使用RemeberMe的用户可访问
         *         perms["sys:admin:save", "sys:admin:modify"]  对应权限可访问
         *         role["admin", "user"]   对应的角色可访问
         *         logout   注销，执行后会直接跳转到 shiroFilterFactoryBean.setLoginUrl() 设置的url，即登录页面
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

//    /**
//     * Shiro生命周期处理器:
//     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
//     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
//     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
