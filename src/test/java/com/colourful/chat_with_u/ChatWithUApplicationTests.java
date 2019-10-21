package com.colourful.chat_with_u;

import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatWithUApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private UserDao userDao;

    @Test
    public void gg(){
/*        List<User> list = userDao.findFriends("root");
        for (User user : list)
        {
            System.out.println(user.getUsername());
        }*/

    }
}
