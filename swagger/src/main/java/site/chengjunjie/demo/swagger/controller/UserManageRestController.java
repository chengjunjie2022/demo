package site.chengjunjie.demo.swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/user")
public class UserManageRestController {

    @GetMapping( "/getUser")
    public String getUser(int userId){
        return "zhangsan";
    }
}
