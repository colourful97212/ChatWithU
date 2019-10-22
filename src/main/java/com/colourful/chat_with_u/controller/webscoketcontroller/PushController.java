package com.colourful.chat_with_u.controller.webscoketcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.vo.Message;
import com.colourful.chat_with_u.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

/**
 * 管理员全部通知接口
 */
@Slf4j
@Component
@ServerEndpoint("/root/all")
public class PushController
{
    /**
     * 因为spring管理的都是单例（singleton），和 websocket （多对象）相冲突，所以会注入是失败报错空指针
     * 因此采用如下方法注入UserDao
     */
    private static UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao)
    {
        PushController.userDao = userDao;
    }

    @OnOpen
    public void onOpen()
    {
        log.info("超级用户连接");
    }

    @OnClose
    public void onClose()
    {
        log.info("超级用户断开");
    }

    @OnMessage
    public void onMessage(String message) throws IOException
    {
        JSONObject data = JSON.parseObject(message);
        String content = data.getString("content");
        Message msg = new Message()
                .setContent(content)
/*                .setFromUser("管理员")*/
                .setType(Message.NOTICE);
        List<User> list = userDao.findAll();
        for (User user : list){
            String username = user.getUsername();
            P2PController.sendMessage(msg,username);
        }
    }

    @OnError
    public void onError(Throwable error)
    {
        System.out.println("websocket发生错误");
        error.printStackTrace();
    }
}
