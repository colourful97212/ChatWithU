package com.colourful.chat_with_u.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
/*    @Autowired
    private MyUserService myUserService;*/
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
     /*   http.authorizeRequests()
                .antMatchers("/login.html")    // 不需要登录就可以访问
                .permitAll()
                .antMatchers("/user/**").hasAnyRole("USER","ROOT") // 需要具有ROLE_USER角色才能访问
                .antMatchers("/root/**").hasAnyRole("ROOT") // 需要具有ROLE_ADMIN角色才能访问
                .antMatchers("/vip/**").hasAnyRole("VIP","ROOT") // 需要具有ROLE_ADMIN角色才能访问
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login.html") // 设置登录页面
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/Test.html"); // 设置默认登录成功后跳转的页面*/
     http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
             .loginPage("/login.html")
             .loginProcessingUrl("/login")
             .defaultSuccessUrl("/protected/Communication.html")
             .and()
             .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
             .antMatchers("/*").permitAll()     // 设置所有人都可以访问登录页面
             .anyRequest()               // 任何请求,登录后可以访问
             .authenticated()
             .and()
             .csrf().disable()           // 关闭csrf防护
             .logout()
             .logoutUrl("/logout")
             .logoutSuccessHandler(myLogoutSuccessHandler)
             .deleteCookies("JSESSIONID")
             .permitAll();

    }

    // BrowerSecurityConfig
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

/*    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(myUserService);
    }*/
}
