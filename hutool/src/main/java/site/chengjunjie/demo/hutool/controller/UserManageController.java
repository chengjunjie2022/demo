package site.chengjunjie.demo.hutool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/user")
public class UserManageController {

    @GetMapping( "/getUser")
    public String getUser(int userId){
        return "zhangsan";
    }
}
