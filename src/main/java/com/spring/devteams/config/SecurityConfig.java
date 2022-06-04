package com.spring.devteams.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(encoder)
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, password, enable from account where email = ?")
                .authoritiesByUsernameQuery("select email, role from account where email = ?");

    }
//09752764717
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/resources/**","/infoForm","/findadmin","/forgetPassword","/restartPassword","/login","/forget","/sign-up","/","course/addCourse","/confirm","/course/search","/detail/**")
                .permitAll()
                .antMatchers("/member/**").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/owner/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/doLogout", "GET"))
                .logoutSuccessUrl("/");

    }

}
