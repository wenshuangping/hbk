package com.github.gateway.component.dto;


import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private String randomStr;
    private String code;
    private String pwType;
    private String source;
}
