package site.chengjunjie.demo.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.chengjunjie.demo.swagger.dto.req.UserModResp;
import site.chengjunjie.demo.swagger.dto.resp.UserResp;

@Slf4j
@Api(tags = "用户管理")
@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @GetMapping( "/getUser")
    @ResponseBody
    public UserResp getUser(@ApiParam("用户ID") long userId){
        log.info("GET /manage/user/getUser -> userId={}", userId);
        return new UserResp().setId(1L).setName("张三").setAge((short)18).setEmail("zhangsan@163.com");
    }

    @PostMapping("/modUser")
    @ResponseBody
    public String modUser(@ApiParam("用户修改信息参数") UserModResp req){
        log.info("POST /manage/user/modUser -> RequestParam={}", req.toString());
        return "true";
    }
}
