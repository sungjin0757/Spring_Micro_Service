package com.example.First.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/first-service")
@Slf4j
@RequiredArgsConstructor
public class FirstServiceController {
    private final Environment environment;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the First Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request")String header){
        log.info(header);
        return "Hello World in FirstService";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request){
        log.info("Server.port={}",request.getServerPort());
        return String.format("Hi, first Service on Port %s",environment.getProperty("local.server.port"));
    }
}
