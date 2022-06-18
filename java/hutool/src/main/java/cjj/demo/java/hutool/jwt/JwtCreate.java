package cjj.demo.java.hutool.jwt;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

public class JwtCreate {
    public static void main(String[] args) {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, 1);

        Map<String,Object> payload = new HashMap<String,Object>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put("loginName", "zhangsan");
        payload.put("pwd", "123456");

        String key = "salt";
        String token = JWTUtil.createToken(payload, key.getBytes());

        System.out.println(token);
    }
}
