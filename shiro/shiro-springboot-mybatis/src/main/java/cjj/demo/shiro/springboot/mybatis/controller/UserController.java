package cjj.demo.shiro.springboot.mybatis.controller;

import cjj.demo.shiro.springboot.mybatis.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/login")
    public String login(String username,String password){
        try {
            userService.checkLogin(username,password);
            return "suc";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
