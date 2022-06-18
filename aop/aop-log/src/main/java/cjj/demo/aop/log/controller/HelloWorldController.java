package cjj.demo.aop.log.controller;

import cjj.demo.aop.log.dto.req.LoginInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo/aop")
public class HelloWorldController {

    @RequestMapping(method = {RequestMethod.POST}, value = "login")
    public String login(@RequestBody LoginInfo loginInfo){
        return "login";
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "getByName")
    public String getByName(@RequestParam String name){
        return "getByName";
    }
}
