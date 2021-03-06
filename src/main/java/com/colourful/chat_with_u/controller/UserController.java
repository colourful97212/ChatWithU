package com.colourful.chat_with_u.controller;

import com.colourful.chat_with_u.controller.webscoketcontroller.P2PController;
import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.service.UserService;
import com.colourful.chat_with_u.utils.JsonResult;
import com.colourful.chat_with_u.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class UserController
{
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult register(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        int row = userService.register(username, password);
        if (row == 1)
        {
            return new JsonResult().setCode(200).setMessage("注册成功");
        }
        else if (row == 2)
        {
            return new JsonResult().setMessage("用户已存在").setCode(200);
        }
        else
        {
            return new JsonResult().setCode(500).setMessage("发生未知错误");
        }
    }

    /**
     * 添加好友
     *
     * @param username 发起请求用户
     * @param friend   被请求用户
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/protected/user/addFriend", method = RequestMethod.POST)
    public JsonResult addFriend(@RequestParam("username") String username, @RequestParam("friend") String friend) throws IOException
    {
        if (userDao.findByUsername(friend) == null)
        {
            log.info("用户" + friend + "不存在");
            return new JsonResult().setCode(200).setMessage("用户名" + friend + "不存在，请您查证后添加正确用户");
        }
        if (userService.isFriends(username, friend))
        {
            return new JsonResult().setMessage("您与" + friend + "已是好友请勿重复添加").setCode(200);
        }
        else
        {
            Message msg = new Message().setToUser(friend).setFromUser(username).setContent(username + "请求添加您为好友").setType(Message.FRIEND_REQUEST);
            P2PController.sendMessage(msg);
            return new JsonResult().setMessage("好友请求已发送给" + friend).setCode(200);
        }
    }

    /**
     * 用户是否同意好友请求
     *
     * @param username 被请求用户
     * @param who      发起好哟请求用户
     * @param isTrue   被请求用户是否同意好友请求
     * @return JsonResult
     * @throws IOException sendMessage抛出的异常
     */
    @RequestMapping(value = "/protected/user/responseRequest", method = RequestMethod.POST)
    public JsonResult responseRequest(@RequestParam("username") String username, @RequestParam("who") String who, @RequestParam("isTrue") Boolean isTrue) throws IOException
    {
        if (isTrue)
        {
            if (userService.addFriend(username, who))
            {
                log.info(username + "同意" + who + "的好友请求");
                Message msg = new Message().setType(Message.FRIEND_RESPONSE).setContent(username + "已接受了您的好友请求").setToUser(who).setFromUser(username);
                P2PController.sendMessage(msg);
                userService.updateFriends(username);
                userService.updateFriends(who);
                return new JsonResult().setCode(200).setMessage("您已成功添加" + who + "为好友");
            }
            else
            {
                log.info(username + "与" + who + "建立好友关系失败");
                Message msg = new Message().setType(Message.FRIEND_RESPONSE).setContent("出现系统错误，请重新尝试添加" + username + "为您的好友，或请与管理员取得联系").setToUser(who).setFromUser(username);
                P2PController.sendMessage(msg);
                return new JsonResult().setCode(200).setMessage("未知错误，建立好友关系失败");
            }
        }
        else
        {
            log.info(username + "拒绝" + who + "的好友请求");
            Message msg = new Message().setType(Message.FRIEND_RESPONSE).setContent(username + "已拒绝了您的好友请求").setToUser(who).setFromUser(username);
            P2PController.sendMessage(msg);
            return new JsonResult().setCode(200).setMessage("您已拒绝" + who + "的好友请求");
        }
    }

    /**
     * 查该用户好友列表
     *
     * @param username 用户名
     * @return
     */
    @RequestMapping(value = "/protected/user/friendsList", method = RequestMethod.POST)
    public JsonResult<Object> friendsList(@RequestParam("username") String username)
    {
        List<String> list = userService.cacheFriends(username);
        System.out.println("Controller被请求一次");
        return new JsonResult<>().setCode(200).setMessage("Success").setData(list);
    }

/*    @RequestMapping(value = "/protected/user/test",method = RequestMethod.GET)
    public JsonResult<Object> updata(@RequestParam("username") String username)
    {
        List<String> list = userService.updateFriends(username);
        System.out.println("Controller被请求一次");
        return new JsonResult<>()
                .setCode(200)
                .setMessage("Success")
                .setData(list);
    }*/

    /**
     * 删除好友
     *
     * @param username 用户名
     * @param friend   要删除好友关系的用户
     * @return
     */
    @RequestMapping(value = "/protected/user/removeFriend", method = RequestMethod.POST)
    public JsonResult<Boolean> removeFriend(@RequestParam("username") String username, @RequestParam("friend") String friend)
    {
        if (!userService.isFriends(username, friend))
        {
            return new JsonResult<Boolean>().setMessage("您与" + friend + "并不是好友关系无需删除好友").setCode(200).setData(false);
        }
        else
        {
            int row = userDao.removeFriend(username, friend);
            if (row == 1)
            {
                userService.updateFriends(username);
                userService.updateFriends(friend);
                return new JsonResult<Boolean>().setMessage("您已成功删除" + friend).setCode(200).setData(true);
            }
            else
            {
                return new JsonResult<Boolean>().setMessage("删除失败，请稍后尝试").setCode(200).setData(false);
            }
        }
    }
}
