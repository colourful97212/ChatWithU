package com.colourful.chat_with_u.service.impl;

import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.service.UserService;
import com.colourful.chat_with_u.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    @CachePut(value = "friends" , key = "#username1")
    public Boolean addFriend(String username1, String username2)
    {
        int row;
        if(!isFriends(username1,username2))
        {
            log.info(username1+"与"+username2+"非好友关系");
            row = userDao.addFriend(username1,username2);
            if (row == 1)
            {
                log.info("添加好友关系成功");
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
            log.info(username1+"与"+username2+"已经是好友关系，不可重复添加好友关系");
            return false;
        }
    }

    /**
     *删除好友
     * @param username1 用户
     * @param username2 好友
     * @return
     */
    @Override
    @CacheEvict(value = "friends" ,key = "#username1")
    //@CacheEvict 从缓存friends中删除key为username2 的数据
    public Boolean removeFriend(String username1, String username2)
    {
        int row;
        if(isFriends(username1,username2))
        {
            log.info(username1+"与"+username2+"是好友关系");
            row = userDao.removeFriend(username1,username2);
            if (row == 1)
            {
                log.info("解除好友关系成功");
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
            log.info(username1+"与"+username2+"非好友关系，不可解除非好友关系");
            return false;
        }
    }

    /**
     * 是否为好友关系
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public Boolean isFriends(String username1, String username2)
    {
        return userDao.isFriend(username1, username2) != 0;
    }

    /**
     * 找朋友
     * @param username
     * @return
     */
    @Override
    @Cacheable(value = "friends" ,key = "#username")
    //@CachePut缓存新增的或更新的数据到缓存,其中缓存名字是 friends 。数据的key是username
    public List<String> findFriends(String username)
    {
        System.out.println("只执行一次");
        return userDao.findFriends(username);
    }


}
