package com.example.for_testdemo1.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserLoginDto;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Mapper.UserMapper;
import com.example.for_testdemo1.Service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(PasswordEncoder passwordencoder){
        this.passwordEncoder = passwordencoder;
    }

    //为什么都是用反射注入，怎么不用构造方法或者setting注入
    @Override
    public Result<UserEntity> register(UserRegisterDto dto) {
        UserEntity exist = lambdaQuery().eq(UserEntity::getAccount, dto.getAccount()).one();
        if (exist != null) {
            return Result.error(400, "账号已存在");
        }
        UserEntity user = new UserEntity();
        user.setAccount(dto.getAccount());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        save(user);
        return Result.success(user);
    }

    @Override
    public Result<String> login(UserLoginDto dto) {
        UserEntity user = lambdaQuery().eq(UserEntity::getAccount, dto.getAccount()).one();
        if (user == null) {
            return Result.error(401, "账号或密码错误");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return Result.error(401, "账号或密码错误");
        }
        return Result.success("success");
    }


}
