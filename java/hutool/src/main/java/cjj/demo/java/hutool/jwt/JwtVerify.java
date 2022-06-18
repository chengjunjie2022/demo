package cjj.demo.java.hutool.jwt;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;

public class JwtVerify {
    public static void main(String[] args) {
        String key = "salt";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2NTU1NjIwMDUsImxvZ2luTmFtZSI6InpoYW5nc2FuIiwiZXhwIjoxNjU1NTYyMDY1LCJwd2QiOiIxMjM0NTYiLCJpYXQiOjE2NTU1NjIwMDV9.2Pq9PXNBoyLCMhdT4uJP_0o70AzCs32jXigVNEyEVPA";
        JWT jwt = JWTUtil.parseToken(token);

        boolean verifyKey = jwt.setKey(key.getBytes()).verify();
        System.out.println(verifyKey);

        //检查是否过期
        boolean verifyTime = jwt.validate(0);
        System.out.println(verifyTime);

        JWTPayload payload = jwt.getPayload();
        //获取所有数据
        System.out.println(JSONUtil.toJsonStr(payload.getClaimsJson()));
        //获取制定数据
        System.out.println(payload.getClaim("loginName"));
        System.out.println(payload.getClaim("pwd"));
    }
}
