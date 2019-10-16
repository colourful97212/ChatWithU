package com.colourful.chat_with_u.dao;

import com.colourful.chat_with_u.vo.User;
import org.apache.ibatis.annotations.Param;


public interface UserDao
{
    User findByUsername(@Param("username") String username);
}
