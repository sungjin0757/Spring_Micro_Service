package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;
    
    //권한에 관련된 작업
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
       // http.authorizeRequests().antMatchers("/users/**").permitAll();
       //인증이 된 상태에서만 통과 가능하도록
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress("211.244.119.115")
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{

        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    // 인증에 관련된 작업
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //사용자가 전달한 email과 password를 가지고 인증을 처리
        //사용자 데이터를 검색해오고 select pwd from users where email=?
        //데이터 베이스 패스워드(encrypt되어있음)와 인풋 패스워드 비교
        //빈에 등록되어있는 패스워드 인코더를 통해 인코드 진행
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        //첨에 userService에 오류가 뜸 userDetailService를 상속받아야
    }
}
