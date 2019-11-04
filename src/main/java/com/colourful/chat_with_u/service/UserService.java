package com.colourful.chat_with_u.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    Boolean addFriend(@Param("username1") String username1,@Param("username2") String username2);

    /**
     * 解除好友关系
     * @param username1
     * @param username2
     * @return
     */
    Boolean removeFriend(@Param("username1") String username1,@Param("username2") String username2);

    /**
     * 判断是否为好友
     * @param username1
     * @param username2
     * @return
     */
    Boolean isFriends(@Param("username1") String username1,@Param("username2") String username2);

    /**
     * 做缓存
     * 通过username来找与该用户具有好友关系的人
     * @param username 需要查找朋友的用户
     * @return 返回查到的所有用户
     */
    List<String> cacheFriends(@Param("username") String username);

    /**
     * 更新缓存中的好友列表
     * @param username
     * @return
     */
    List<String> updateFriends(@Param("username") String username);
}
