package cjj.demo.tmpl.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Data
public class JwtConfig {

    /**
     * 发行者
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * 密钥
     */
    @Value("${jwt.secret-key}")
    private String secretKey;

    /**
     * token 过期时间
     */
    @Value("${jwt.access-token-expire-time}")
    private Duration accessTokenExpireTime;

    /**
     * pc端 刷新 token 过期时间
     */
    @Value("${jwt.refresh-token-expire-time}")
    private Duration refreshTokenExpireTime;

    /**
     * app端 刷新 token 过期时间
     */
    @Value("${jwt.refresh-token-expire-app-time}")
    private Duration refreshTokenExpireAppTime;
}
