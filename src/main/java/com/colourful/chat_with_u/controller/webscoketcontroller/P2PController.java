package com.colourful.chat_with_u.controller.webscoketcontroller;

import com.colourful.chat_with_u.vo.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/websocket/{username}")
public class P2PController
{
    private static int onLineCount = 0;//在线人数

    //concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String , Session> webSocketMap = new ConcurrentHashMap<>();

    private Session session; // 与某个客户端的连接会话，需要通过它来给客户端发送数据

    private String username;

    @OnOpen
    public void OnOpen(Session session , @PathParam("username") String username)
    {
        this.session = session;
        this.username = username;
        webSocketMap.put(username,session);
        onLineCount ++;
        System.out.println("当前在线人数:"+onLineCount);
    }

    @OnClose
    public void onClose(@PathParam("username") String username) {
        webSocketMap.remove(username); // 从set中删除
        onLineCount --; // 在线数减1
        System.out.println("当前在线人数:"+onLineCount);
    }

    @OnMessage
    public void onMessage(String message) {
  /*      Session HisSession = webSocketMap.get(toUser);
        try
        {
            HisSession.getBasicRemote().sendText(message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/
/*        String toUser = obj.getToUser();
        String message = obj.getMessage();
        Session HisSession = webSocketMap.get(toUser);
        try
        {
            HisSession.getBasicRemote().sendText(message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("发送数据错误");
        }*/
      /*  System.out.println(username + "To: "+ toUser + "\""+message+"\"");*/
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("websocket发生错误");
        error.printStackTrace();
    }
}
