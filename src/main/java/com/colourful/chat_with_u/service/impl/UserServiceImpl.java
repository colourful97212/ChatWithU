package com.colourful.chat_with_u.service.impl;

import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.service.UserService;
import com.colourful.chat_with_u.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(String username, String password)
    {
        int row;
        User user = userDao.findByUsername(username);
        if (user != null){
            row = 2;
            return row;
        }
        row = userDao.addUser(username,password);
        if (row == 1)
        {
            log.info("注册成功 :{}",user);
        }
        else if (row == 0)
        {
            log.info("注册失败");
        }
        else
        {
            log.info("发生未知错误");
        }
        return row;
    }
}
