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

    /**
     * 添加好友关系
     * @param username1
     * @param username2
     * @return
     */
    Integer addFriend(@Param("username1") String username1,@Param("username2") String username2);

    /**
     * 解除好友关系
     * @param username1
     * @param username2
     * @return
     */
    Integer removeFriend(@Param("username1") String username1,@Param("username2") String username2);
}
