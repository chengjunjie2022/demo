package cjj.demo.tmpl.auth.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import road.cjj.commons.redis.RedisUtil;

/**
 * 缓存管理器
 */
public class RedisCacheManager implements CacheManager {

    // 注入 redis
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache(redisUtil);
    }
}
