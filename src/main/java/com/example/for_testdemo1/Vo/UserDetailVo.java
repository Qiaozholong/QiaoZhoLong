package com.example.for_testdemo1.Vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDetailVo {
    private int id;
    private String account;
    private String name;
    private String gender;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
