package com.example.for_testdemo1.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private int id;
    private String account;
    private String name;
    private String gender;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
