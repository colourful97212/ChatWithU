package com.colourful.chat_with_u.service;

import org.apache.ibatis.annotations.Param;

public interface UserService
{
    /**
     * 用于注册的接口
     * @param username
     * @param password
     * @return
     */
    Integer register(@Param("username")String username,@Param("password") String password);
}
