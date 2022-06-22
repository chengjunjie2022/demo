package cjj.demo.tmpl.auth.shiro;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.druid.support.json.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import road.cjj.commons.redis.RedisUtil;
import road.cjj.commons.redis.consts.RedisConst;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义 角色 权限 缓存 ，继承 Shiro 的 Cache 接口
 */
@Slf4j
public class RedisCache<K,V> implements Cache<K,V> {

    private String cacheKey;// key
    private long expire = 24; // 缓存一天，过期时间
    private RedisUtil redisUtil;

    public RedisCache(RedisUtil redisUtil){
        this.cacheKey = RedisConst.IDENTIFY_CACHE_KEY;
        this.redisUtil = redisUtil;
    }

    // 解析 token，缓存 key
    private String getRedisCacheKey(K key){
        if(null==key){
            return null;
        }else {
            return this.cacheKey + JWTUtil.parseToken(key.toString()).getPayload().getClaim(JWTPayload.SUBJECT);
        }
    }

    /**
     * 获取缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public V get(K key) throws CacheException {
        log.info("Shiro从缓存中获取数据KEY值[{}]",key);
        if (key == null) {
            return null;
        }
        try{
            String redisCacheKey = getRedisCacheKey(key);
            Object rawValue = redisUtil.get(redisCacheKey);
            if (rawValue == null){
                return null;
            }
            SimpleAuthorizationInfo simpleAuthorizationInfo = JSONUtil.toBean(JSONUtil.toJsonStr(rawValue), SimpleAuthorizationInfo.class);
            V value = (V) simpleAuthorizationInfo;
            return value;
        }catch (Exception e){
            throw new CacheException(e);
        }
    }

    /**
     * 设置缓存
     * @param key
     * @param value
     * @return
     * @throws CacheException
     */
    @Override
    public V put(K key, V value) throws CacheException {
        log.info("put key [{}]",key);
        if (key == null) {
            log.warn("Saving a null key is meaningless, return value directly without call Redis.");
            return value;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            redisUtil.set(redisCacheKey,value == null ? null : value,expire, TimeUnit.HOURS);
            return value;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    /**
     * 删除 key
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public V remove(K key) throws CacheException {
        log.info("remove key [{}]",key);
        if (key == null){
            return null;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            Object rawValue = redisUtil.get(redisCacheKey);
            V previous = (V) rawValue;
            redisUtil.delete(redisCacheKey);
            return previous;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    /**
     * 清空
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {
        log.debug("clear cache");
        Set<String> keys = null;
        try {
            keys = redisUtil.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        if (keys == null || keys.size() == 0) {
            return;
        }
        for (String key: keys) {
            redisUtil.delete(key);
        }
    }

    /**
     * 获取 key 长度
     * @return
     */
    @Override
    public int size() {
        int result = 0;
        try {
            result = redisUtil.keys(this.cacheKey + "*").size();
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        return result;
    }

    @Override
    public Set keys() {
        Set<String> keys = null;
        try {
            keys = redisUtil.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<K> convertedKeys = new HashSet<>();
        for (String key:keys) {
            try {
                convertedKeys.add((K) key);
            } catch (Exception e) {
                log.error("deserialize keys error", e);
            }
        }
        return convertedKeys;
    }

    @Override
    public Collection values() {
        Set<String> keys = null;
        try {
            keys = redisUtil.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get values error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        List<V> values = new ArrayList<V>(keys.size());
        for (String key : keys) {
            V value = null;
            try {
                value = (V) redisUtil.get(key);
            } catch (Exception e) {
                log.error("deserialize values= error", e);
            }
            if (value != null) {
                values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }
}
