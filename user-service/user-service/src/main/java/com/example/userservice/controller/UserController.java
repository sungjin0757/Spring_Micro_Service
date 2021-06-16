package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status(){

        return String.format("It's Working, I'm User-Service on PORT ="
                +env.getProperty("local.server.port")
                +", Port(server) = "+env.getProperty("server.port")
                +", token secret = "+env.getProperty("token.secret")
                +", token expiration time = "+env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String welcome(){
    //    return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser=mapper.map(userDto,ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        List<UserDto> userByAll = userService.getUserByAll();
        List<ResponseUser> collect = userByAll.stream()
                .map(u -> new ResponseUser(u.getEmail(), u.getName(), u.getUserId(), u.getOrders()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(collect);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId")String userId){
        UserDto dto = userService.getUserByUserId(userId);
        ResponseUser responseUser=new ModelMapper().map(dto,ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);

    }


}
