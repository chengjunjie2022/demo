package cjj.demo.cache.redis.base.controller;

import cjj.demo.cache.redis.base.dto.req.UserReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import road.cjj.commons.redis.RedisUtil;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cache/redis")
public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/add")
    public void add(String key, String val, long validMinutes){
        redisUtil.set(key, val, validMinutes, TimeUnit.MINUTES);
    }

    @GetMapping("/get")
    public String get(String key){
        return (String)redisUtil.get(key);
    }

    @GetMapping("/del")
    public void del(String key){
        redisUtil.delete(key);
    }

    @PostMapping("/addObj")
    public void addObj(@RequestParam("key") String key, @RequestBody UserReqVo user){
//        redisUtil.hset(key, user);
    }

}
