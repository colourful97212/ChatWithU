package com.colourful.chat_with_u.service.impl;

import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.service.UserService;
import com.colourful.chat_with_u.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Integer register(String username, String password)
    {
        int row;
        User user = userDao.findByUsername(username);
        if (user != null)
        {
            row = 2;
            return row;
        }
        row = userDao.addUser(username, password);
        if (row == 1)
        {
            log.info("注册成功 :{}", user);
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
     *
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public Boolean addFriend(String username1, String username2)
    {
        int row;
        if (!isFriends(username1, username2))
        {
            log.info(username1 + "与" + username2 + "非好友关系");
            row = userDao.addFriend(username1, username2);
            if (row == 1)
            {
                log.info("添加好友关系成功");
                updateFriends(username1);
                updateFriends(username2);//更新好友列表缓存
                return true;
            }
            else
            {
                log.info("添加好友关系失败");
                return false;
            }
        }
        else
        {
            log.info(username1 + "与" + username2 + "已经是好友关系，不可重复添加好友关系");
            return false;
        }
    }

    /**
     * 删除好友
     *
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public Boolean removeFriend(String username1, String username2)
    {
        int row;
        if (isFriends(username1, username2))
        {
            log.info(username1 + "与" + username2 + "是好友关系");
            row = userDao.removeFriend(username1, username2);
            if (row >= 1)
            {
                log.info("解除好友关系成功");
                updateFriends(username1);
                updateFriends(username2);//更新好友列表缓存
                return true;
            }
            else
            {
                log.info("解除好友关系失败");
                return false;
            }
        }
        else
        {
            log.info(username1 + "与" + username2 + "非好友关系，不可解除非好友关系");
            return false;
        }
    }

    /**
     * 是否为好友关系
     *
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public Boolean isFriends(String username1, String username2)
    {
        return userDao.isFriend(username1, username2) != 0;
    }

    @Override
    @Cacheable(value = "friends", key = "#username")//有key之后不会再执行Service代码
    //执行一次
    //Controller被请求一次
    //Controller被请求一次
    //Controller被请求一次
    public List<String> cacheFriends(String username)
    {
        System.out.println("执行一次");
        return userDao.findFriends(username);
    }

    /**
     * 强制更新缓存起来的用户好友列表
     *
     * @param username
     * @return
     */
    @CachePut(value = "friends", key = "#username")
    public List<String> updateFriends(String username)
    {
        System.out.println("更新" + username + "的好友列表");
        return userDao.findFriends(username);
    }

}
