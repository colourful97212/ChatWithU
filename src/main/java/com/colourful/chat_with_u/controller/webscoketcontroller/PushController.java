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
    @Autowired
    private UserDao userDao;

    @OnOpen
    public void onOnpen()
    {
        log.info("超级拥有连接");
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
        Message msg = new Message().setContent(content);
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
