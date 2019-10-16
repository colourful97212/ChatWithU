package com.colourful.chat_with_u.Hello;

import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Gg
{
    @Autowired
    UserDao userDao;

    @Test
    public void gg(){
        User user = userDao.findByUsername("root");
        System.out.println(user);
    }
}
