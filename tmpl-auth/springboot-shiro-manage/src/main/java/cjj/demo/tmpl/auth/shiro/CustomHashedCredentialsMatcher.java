package cjj.demo.tmpl.auth.shiro;

import cjj.demo.tmpl.auth.config.JwtConfig;
import cjj.demo.tmpl.auth.exception.BizException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import road.cjj.commons.entity.RC;
import road.cjj.commons.redis.RedisUtil;
import road.cjj.commons.redis.consts.RedisConst;

import java.nio.charset.StandardCharsets;

/** 自定义的认证器
 *
 * Shiro 的 认证器 HashedCredentialsMatcher
 * Shiro主要是用过 HashedCredentialsMatcher 的 doCredentialsMatch方法对提交的用户名密码组装的 token 进行效验，我们这使用的是 jwt-token
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 真正的效验（主要是通过 HashedCredentialsMatcher 的 doCredentialsMatch 方法返回值进行效验）
     * 我们使用的是 jwt-token ，所以在此处效验
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) token;
        String accessToken = (String) customUsernamePasswordToken.getPrincipal();
        JWT jwt = JWTUtil.parseToken(accessToken);
        JWTPayload payload = jwt.getPayload();
        Long adminid = (Long) payload.getClaim("adminid");

        /**
         * 判断用户是否被锁定
         */
        if (redisUtil.hasKey(RedisConst.ACCOUNT_LOCK_KEY + adminid)){
            throw new BizException(RC.ERR_ACCOUNT_LOCK.getCode(), RC.ERR_ACCOUNT_LOCK.getMsg());
        }

        /**
         * 判断用户是否被删除
         */
        if (redisUtil.hasKey(RedisConst.DELETED_USER_KEY + adminid)){
            throw new BizException(RC.ERR_ACCOUNT_DELETED.getCode(), RC.ERR_ACCOUNT_DELETED.getMsg());
        }

        /**
         * 判断用户是否在加入黑名单 禁止再访问我们的系统资源，让用户重新登录
         */
        if (redisUtil.hasKey(RedisConst.JWT_USER_LOGIN_BLACKLIST + adminid)){
            throw new BizException(RC.ERR_TOKEN_PAST.getCode(), RC.ERR_TOKEN_PAST.getMsg());
        }
        /**
         * 判断token是否通过校验（不通过，token 失效）
         */
        if (!jwt.setKey(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8)).verify()){
            throw new BizException(RC.ERR_TOKEN_PAST.getCode(), RC.ERR_TOKEN_PAST.getMsg());
        }

        if (redisUtil.hasKey(RedisConst.JWT_LOGIN_NAME + adminid)){
            String userAccessToken = (String) redisUtil.get(RedisConst.JWT_LOGIN_NAME + adminid);
            if (!accessToken.equals(userAccessToken)){
                throw new BizException(RC.ERR_TOKEN_EXISTS.getCode(), RC.ERR_TOKEN_EXISTS.getMsg());
            }
        }

        return true;
    }
}
