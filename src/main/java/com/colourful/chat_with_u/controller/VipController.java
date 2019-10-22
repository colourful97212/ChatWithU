package com.colourful.chat_with_u.controller;

import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.service.UserService;
import com.colourful.chat_with_u.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vip")
public class VipController
{
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String gg(){
        return "Hello vip";
    }

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public void ggg(){
        /*Integer bo = userDao.isFriend("root","阿起");
        System.out.println(bo);*/
/*        Integer row = userService.addFriend("root","阿秃");
        System.out.println(row);*/
    }
}
