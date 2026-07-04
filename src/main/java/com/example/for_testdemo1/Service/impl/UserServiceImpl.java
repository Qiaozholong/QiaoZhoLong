package com.example.for_testdemo1.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Mapper.UserMapper;
import com.example.for_testdemo1.Service.UserService;
import com.example.for_testdemo1.Vo.UserLoginVo;
import com.example.for_testdemo1.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordencoder) {
        this.passwordEncoder = passwordencoder;
    }

    @Override
    public Result<UserRegisterDto> userRegister(UserRegisterDto dto) {
        UserEntity exist = lambdaQuery().eq(UserEntity::getAccount, dto.getAccount()).one();
        if (exist != null) {
            return Result.error(400, "账号已存在");
        }
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(dto,user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        save(user);
        UserRegisterDto result = new UserRegisterDto();
        BeanUtils.copyProperties(user,result);
        result.setPassword(null);
        return Result.success(result);
    }

    @Override
    public Result<UserLoginVo> userLogin(UserLoginVo vo) {
        UserEntity user = lambdaQuery().eq(UserEntity::getAccount, vo.getAccount()).one();
        if (user == null) {
            return Result.error(401, "账号或密码错误");
        }
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return Result.error(401, "账号或密码错误");
        }
        UserLoginVo result = new UserLoginVo();
        result.setAccount(user.getAccount());
        result.setPassword("不给看");
        return Result.success(result);
    }
    @Override
    public Result<UserVo> userInfo(int id){
        UserEntity user= getById(id);
        if(user==null){return Result.error(400,"用户不存在");}
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user,vo);
        return  Result.success(vo);
    }


}
