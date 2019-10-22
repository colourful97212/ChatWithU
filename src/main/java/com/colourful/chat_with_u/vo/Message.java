package com.colourful.chat_with_u.vo;


import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Message
{
    private String toUser;
    private String fromUser;
    private String content;
    private Integer type;
    //type 常量
    public static final Integer MESSAGE = 1;
    public static final Integer NOTICE = 2;
    public static final Integer FRIEND_REQUEST = 3;
    public static final Integer FRIEND_RESPONSE = 4;
}
