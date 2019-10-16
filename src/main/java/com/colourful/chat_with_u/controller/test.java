package com.colourful.chat_with_u.controller;

import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test
{
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public void helo(){
        User user = userDao.findByUsername("root");
        System.out.println(user);
    }
}
