package com.example.userservice.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class Greeting {
    @Value("${greeting.message}")
    private String message;
}
