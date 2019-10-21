package com.colourful.chat_with_u.config;

import com.colourful.chat_with_u.dao.UserDao;
import com.colourful.chat_with_u.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MyUserService implements UserDetailsService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User userPojo = userDao.findByUsername(username);
        if (userPojo == null)
        {
            throw new RuntimeException("该用户不存在");
        }
        String password = passwordEncoder.encode(userPojo.getPassword());
        String roleString = userPojo.getAuthority();
        List<GrantedAuthority> authorityLists = AuthorityUtils.commaSeparatedStringToAuthorityList(roleString);
        log.info("用户的用户名: {}", username);
        log.info("用户密码: {}", password);
        log.info("用户权限: {}", authorityLists);
        return new org.springframework.security.core.userdetails.User(
                username,password, authorityLists
        );
    }
}
