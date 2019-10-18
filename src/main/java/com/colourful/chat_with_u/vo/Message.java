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
}
