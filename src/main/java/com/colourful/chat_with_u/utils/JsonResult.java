package com.colourful.chat_with_u.utils;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JsonResult<T>
{
    private Integer code;
    private String message;
    private T data;
}
