package com.colourful.chat_with_u.dao;

import com.colourful.chat_with_u.vo.User;
import org.apache.ibatis.annotations.Param;


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
}
