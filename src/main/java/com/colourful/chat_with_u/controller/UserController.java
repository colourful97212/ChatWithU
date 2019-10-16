package com.colourful.chat_with_u.controller;

import com.colourful.chat_with_u.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/test",method = RequestMethod.GET)
    public String gg(){
        return "Hello user";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String regiester(@RequestParam("username") String username,@RequestParam("password") String password){
        int row = userService.register(username,password);
        if (row == 1)
        {
            return "注册成功";
        }
        else if (row == 2)
        {
            return "用户已存在";
        }
        else
        {
            return "发生未知错误";
        }
    }
}
