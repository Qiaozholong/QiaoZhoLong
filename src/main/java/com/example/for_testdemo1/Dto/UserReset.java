package com.example.for_testdemo1.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserReset {
    private String account;
    private String password;
    private String newone;
}
