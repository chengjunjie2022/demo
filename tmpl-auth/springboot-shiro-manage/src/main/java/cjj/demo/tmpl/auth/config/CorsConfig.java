package cjj.demo.tmpl.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    /**
     * 跨域配置
     * @return
     */
    private CorsConfiguration apiCorsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //放行哪些原始域
        corsConfiguration.addAllowedOriginPattern("*");

        // 是否允许用户发送、处理 cookie
        corsConfiguration.setAllowCredentials(true);

        //放行哪些原始请求头部信息
        corsConfiguration.addAllowedHeader("Origin");
        corsConfiguration.addAllowedHeader("X-Requested-With");
        corsConfiguration.addAllowedHeader("Content-Type");
        corsConfiguration.addAllowedHeader("Accept");
        corsConfiguration.addAllowedHeader("authorization");

        //放行哪些请求方式
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);

        // 预检请求的有效期，单位为秒。
        corsConfiguration.setMaxAge(3600L);

        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(){

        //添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", apiCorsConfig());

        return new CorsFilter(source);
    }
}
