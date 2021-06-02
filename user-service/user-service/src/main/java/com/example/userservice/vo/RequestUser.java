package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {
    @NotNull(message = "Email Cannot Be Null!")
    @Size(min=2,message = "Email Required More Than Two Characters!")
    private String email;

    @NotNull(message = "Name Cannot Be Null!")
    @Size(min=2,message = "Name Required More Than Two Characters!")
    private String name;

    @NotNull(message = "Password Cannot Be Null!")
    @Size(min=8,message = "Password Required More Than Eight Characters!")
    private String password;
}
