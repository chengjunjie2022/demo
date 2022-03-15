package site.chengjunjie.demo.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.chengjunjie.demo.swagger.dto.req.UserModResp;
import site.chengjunjie.demo.swagger.dto.resp.UserResp;

@Slf4j
@Api(tags = "用户管理Restful")
@RestController
@RequestMapping("/manager/user")
public class UserManageRestfulController {

    @GetMapping( "/{userId}")
    public UserResp getUser(@PathVariable("userId") long userId){
        log.info("GET /manager/user//{userId} -> userId={}", userId);
        return new UserResp().setId(1L).setName("张三").setAge((short)18).setEmail("zhangsan@163.com");
    }

    @PostMapping("/{userId}")
    public boolean modUser(@PathVariable("userId") @ApiParam("用户ID") long userId){
        log.info("POST /manager/user/{userId} -> userId={}", userId);
        return true;
    }
    @PostMapping("/modUser")
    public String modUser(@RequestBody @ApiParam("用户修改信息参数") UserModResp req){
        log.info("POST /manager/user/modUser -> requestBody={}", req.toString());
        return "true";
    }
}
