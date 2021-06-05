package com.example.userservice.service;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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
        List<ResponseOrder> orders=new ArrayList<>();
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
}
