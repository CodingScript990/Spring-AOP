package com.example.aop.dto;

import lombok.Data;

@Data
public class UserDto {
    // Member Field
    private Long id;
    private String username;
    private String password;
    private String passwordCheck;
    private String email;
    private String phone;
}
