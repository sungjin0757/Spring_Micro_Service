package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.domain.UserEntity;
import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final Environment env;
    private final OrderServiceClient orderServiceClient;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(userEntity);

        UserDto map = mapper.map(userEntity, userDto.getClass());
        return map;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity=userRepository.findByUserId(userId);
        if (userEntity==null)
            throw new UsernameNotFoundException("User not found");
        UserDto userDto=new ModelMapper().map(userEntity,UserDto.class);

//        List<ResponseOrder> orders=new ArrayList<>();
        //using rest template
//        String orderUrl="http://127.0.0.1:8000/order-service/%s/orders";
//        String orderUrl=String.format(env.getProperty("order_service.url"),userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });
//        List<ResponseOrder> orderList = orderListResponse.getBody();
//        userDto.setOrders(orderList);
//        List<ResponseOrder> orders=null;
//        try {
//            orders = orderServiceClient.getOrders(userId);
//        }catch (FeignException fe){
//            log.error(fe.getMessage());
//        }
        List<ResponseOrder> orders = orderServiceClient.getOrders(userId);
        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public List<UserDto> getUserByAll() {
        Iterable<UserEntity> all = userRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<UserDto> dto=new ArrayList<>();
        all.forEach(a->{
            dto.add(mapper.map(a,UserDto.class));
        });

        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByEmail(userName);

        if(entity==null){
            throw new UsernameNotFoundException(userName);
        }

        //userdetailservice????????? user????????? ??????
        //????????? ????????? ????????? ???????????? ??????
        //??????????????? ??? ???????????? ????????? ????????? ?????? ??????
        return new User(entity.getEmail(), entity.getEncryptedPwd(),
                true,true,true,true,
                new ArrayList<>());

        //arraylist??? ?????? ????????? ????????? ????????? ??? ????????? ??????????????? ????????? ???????????? ????????? ??????
        //?????? ?????????????????? ????????? ?????? ????????? ?????? ??? ???????????? ?????????
    }

    @Override
    public UserDto getUserDetailsByEmail(String username) {
        UserEntity entity = userRepository.findByEmail(username);

        if (entity==null){
            throw new UsernameNotFoundException(username);
        }
        UserDto userDto=new ModelMapper().map(entity,UserDto.class);
        return userDto;
    }
}
