package com.colourful.chat_with_u.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vip")
public class VipController
{
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String gg(){
        return "Hello vip";
    }
}
