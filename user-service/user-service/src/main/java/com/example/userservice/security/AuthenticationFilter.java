package com.example.userservice.security;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        //요청 정보를 보냈을 때 처리 시켜 줄 수 있는 메소드
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            //전달 받은 Input 스트림을 우리의 클래스로 변환 받은 이유는 post로 받았기 때문에 파라미터에 접근을 하지 못함
            // 이렇게 하면 request로 들어온 정보를 직접 처리 가능

            //인증 정보로 만들기 위해 UsernamePasswordAuthenticationFilter 에 전달 필요
            //이 값을 사용하기 위해  입력받은 정보를 Spring Security Web Authentication 패키지 안에 있는 UsernamePasswordAuthentication Token
            //으로 변경 필요

            //최종적으로 이 값을 Authentication에 전달을 해서 인증 처리를 해야함
           return getAuthenticationManager().authenticate(
                   new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(),new ArrayList<>())
           );
           //이 변경된 토큰으로 Authentication manager로 인증 작업을 해야함 저 토큰을 인증 처리해줌
            //ArrayList는 권한에 관련된 것을 처리위해
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //실제 Login성공했을 때 토큰을 만드는 등 토큰의 만료시간등등을 정할것
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto=userService.getUserDetailsByEmail(username);

        //token 생성
        String token = Jwts.builder()
                .setSubject(userDto.getUserId())   //userId로 토큰 만듬
                .setExpiration(
                        new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token.expiration_time")))) //현재시간에 하루를 더함
                .signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret"))//암호화
                .compact();

        //token을 가지고 사용자 인증을 해줌
        response.addHeader("token",token);
        response.addHeader("userId",userDto.getUserId());
    }


}
