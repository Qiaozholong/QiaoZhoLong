package com.example.for_testdemo1.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserLoginDto;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Entity.UserEntity;

public interface UserService extends IService<UserEntity> {
    Result<UserEntity> register(UserRegisterDto dto);
    Result<String> login(UserLoginDto dto);

}
