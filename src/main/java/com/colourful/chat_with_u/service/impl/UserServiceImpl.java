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

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
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

    /**
     * 添加好友
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public Integer addFriend(String username1, String username2)
    {
        int row;
        if(userDao.isFriend(username1,username2) == 0)
        {
            log.info(username1+"与"+username2+"非好友关系");
            row = userDao.addFriend(username1,username2);
            if (row == 1)
            {
                log.info("添加好友关系成功");
            }
            else
            {
                log.info("添加好友关系失败");
            }
        }
        else
        {
            log.info(username1+"与"+username2+"已经是好友关系，不可重复添加好友关系");
            row = 0;
        }
        return row;
    }

    /**
     *删除好友
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public Integer removeFriend(String username1, String username2)
    {
        int row;
        if(userDao.isFriend(username1,username2) != 0)
        {
            log.info(username1+"与"+username2+"是好友关系");
            row = userDao.removeFriend(username1,username2);
            if (row == 1)
            {
                log.info("解除好友关系成功");
            }
            else
            {
                log.info("解除好友关系失败");
            }
        }
        else
        {
            log.info(username1+"与"+username2+"非好友关系，不可解除非好友关系");
            row = 0;
        }
        return row;
    }
}
