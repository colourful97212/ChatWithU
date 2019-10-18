package com.colourful.chat_with_u.controller.webscoketcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.colourful.chat_with_u.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{username}")
public class P2PController
{
    private static int onLineCount = 0;//在线人数

    //concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象。
    //用户上线后，保存用户在线的Session，若此map中找不到用户的用户名和sesion说明该用户未上线
    private static ConcurrentHashMap<String , Session> onLineMap = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String , CopyOnWriteArrayList<String>> outLineMessageMap = new ConcurrentHashMap<>();

    private Session session; // 与某个客户端的连接会话，需要通过它来给客户端发送数据

    private String username;

    @OnOpen
    public void OnOpen(Session session , @PathParam("username") String username) throws IOException
    {
        log.info("用户"+username+"已上线");
        this.session = session;
        this.username = username;
        onLineMap.put(username,session);
        onLineCount ++;
        System.out.println(outLineMessageMap);
        System.out.println(username);
        if (outLineMessageMap.containsKey(username))
        {
            log.info("该用户拥有未读消息队列");
            CopyOnWriteArrayList<String> cowList = outLineMessageMap.get(username);
            for (String jsonString : cowList)
            {
                session.getBasicRemote().sendText(jsonString);
                log.info("消息已发送给用户：",jsonString);
                cowList.remove(jsonString);
                log.info("已清除该消息");
            }
            outLineMessageMap.remove(username);
            log.info("已删除该用户未读消息队里");
        }
        log.info("该用户没有未读消息队列");
        log.info("当前在线人数:{}",onLineCount);
    }

    @OnClose
    public void onClose(@PathParam("username") String username) {
        onLineMap.remove(username); // 从set中删除
        onLineCount --; // 在线数减1
        log.info("当前在线人数:{}",onLineCount);
    }

    @OnMessage
    public void onMessage(String message) throws IOException
    {
        log.info("收到消息：{}",message);
        JSONObject data = JSON.parseObject(message);
        String toUser = data.getString("toUser");
        String content = data.getString("content");
        Message msg = new Message()
                .setMessage(content)
                .setFromUser(username)
                .setToUser(toUser);
        String JsonString = JSON.toJSONString(msg);
        if (onLineMap.containsKey(toUser))
        {
            Session session = onLineMap.get(toUser);
            session.getBasicRemote().sendText(JsonString);
            log.info("用户在线,消息已发送给用户"+toUser+"内容为："+content);
        }
        else
        {
            if (outLineMessageMap.containsKey(toUser))
            {
                CopyOnWriteArrayList<String> list = outLineMessageMap.get(toUser);
                list.add(JsonString);
                log.info("用户不在线，已拥有未读消息队列，消息已加入未读队列");
            }
            else
            {
                CopyOnWriteArrayList<String> msgList = new CopyOnWriteArrayList<>();
                msgList.add(JsonString);
                outLineMessageMap.put(toUser,msgList);
                log.info("用户不在线，未拥有未读消息队列，已创建未读消息队列，消息已加入未读队列");
            }

        }
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("websocket发生错误");
        error.printStackTrace();
    }
}
