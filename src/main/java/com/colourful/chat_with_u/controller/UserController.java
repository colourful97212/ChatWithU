package com.colourful.chat_with_u.controller;

import com.colourful.chat_with_u.controller.webscoketcontroller.P2PController;
import com.colourful.chat_with_u.service.UserService;
import com.colourful.chat_with_u.utils.JsonResult;
import com.colourful.chat_with_u.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public JsonResult register(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        int row = userService.register(username,password);
        if (row == 1)
        {
            return new JsonResult()
                    .setCode(200)
                    .setMessage("注册成功");
        }
        else if (row == 2)
        {
            return new JsonResult()
                    .setMessage("用户已存在")
                    .setCode(200);
        }
        else
        {
            return new JsonResult()
                    .setCode(500)
                    .setMessage("发生未知错误");
        }
    }

    @RequestMapping(value = "/protected/user/addFriend",method = RequestMethod.POST)
    public JsonResult addFriend(@RequestParam("username") String username, @RequestParam("friend") String friend) throws IOException
    {
        Message msg = new Message()
                .setToUser(friend)
                .setFromUser("系统提醒")
                .setContent(username+"请求添加您为好友")
                .setType(Message.FRIEND_REQUEST);
        P2PController.sendMessage(msg);
/*        msg.setToUser(username)
                .setFromUser("系统提醒")
                .setContent("好友请求已发送给"+friend)
                .setType(Message.MESSAGE);
        P2PController.sendMessage(msg);*/
        return new JsonResult()
                .setMessage("好友请求已发送给"+friend)
                .setCode(200);
    }
}
