package com.colourful.chat_with_u.dao;

import com.colourful.chat_with_u.vo.User;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao
{
    /**
     * 根据用户名找一个User对象
     * @param username 用户名
     * @return user
     */
    User findByUsername(@Param("username") String username);

    /**
     * 添加一个用户
     * @param username 用户名
     * @param password 密码
     * @return 受影响的行数
     */
    Integer addUser(@Param("username") String username,@Param("password") String password);

    /**
     * 找所有的用户
     * @return 集合
     */
    List<User> findAll();

    /**
     * 通过username来找与该用户具有好友关系的人
     * @param username 需要查找朋友的用户
     * @return 返回查到的所有用户
     */
    List<String> findFriends(@Param("username") String username);

    /**
     * 添加两位用户的好友关系
     * @param username1 两位要建立好友关系的用户
     * @param username2 两位要建立好友关系的用户
     * @return 返回受影响的行数
     */
    Integer addFriend(@Param("username1")String username1,@Param("username2") String username2);

    /**
     * 解除好友关系
     * @param username1 两个用户
     * @param username2 两个用户
     * @return 返回受影响的行数
     */
    Integer removeFriend(@Param("username1") String username1,@Param("username2") String username2);

    /**
     * 判断两人是否是好友
     * @param username1 俩个用户
     * @param username2 俩个用户
     * @return 返回布尔值
     */
    Integer isFriend(@Param("username1") String username1,@Param("username2") String username2 );

    //TODO:模糊查询
    List<User> findLikeUser(@Param("String_1") String String_1);
}
