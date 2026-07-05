package com.example.for_testdemo1.Service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserDto;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Dto.UserReset;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Mapper.UserMapper;
import com.example.for_testdemo1.Service.UserService;
import com.example.for_testdemo1.Vo.UserDetailVo;
import com.example.for_testdemo1.Vo.UserLoginVo;
import com.example.for_testdemo1.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

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
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        save(user);
        UserRegisterDto result = new UserRegisterDto();
        BeanUtils.copyProperties(user, result);
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
    public Result<UserVo> userInfo(int id) {
        UserEntity user = getById(id);
        if (user == null) {
            return Result.error(400, "用户不存在");
        }
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        return Result.success(vo);
    }

    @Override
    public Result<List<UserVo>> userInfo() {
        List<UserEntity> Ue = list();
        List<UserVo> vo = Ue.stream()
                .map(element -> {
                    UserVo v = new UserVo();
                    BeanUtils.copyProperties(element, v);
                    return v;
                }).collect(Collectors.toList());
        return Result.success(vo);
    }

    @Override
    public Result<UserEntity> userAllInfo(int id) {
        UserEntity user = getById(id);
        if (user == null) {
            return Result.error(400, "用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result<List<UserDetailVo>> userAllInfo() {
        List<UserEntity> Ue = list();
        List<UserDetailVo> vo = Ue.stream()
                .map(element -> {
                    UserDetailVo v = new UserDetailVo();
                    BeanUtils.copyProperties(element, v);
                    return v;
                }).collect(Collectors.toList());
        return Result.success(vo);
    }

    @Override
    public Result<Page<UserVo>> page(int current) {
        if (current <= 0) {
            return Result.error(400, "参数非法，请输入大于零的数");
        }
        Page<UserEntity> entityPage = page(new Page<>(current, 10));
        Page<UserVo> voPage = new Page<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());
        voPage.setRecords(entityPage.getRecords().stream().map(element -> {
            UserVo v = new UserVo();
            BeanUtils.copyProperties(element, v);
            return v;
        }).collect(Collectors.toList()));
        return Result.success(voPage);

    }

    @Override
    public Result<List<UserDto>> userSearch(String anyKeyword) {
        List<UserEntity> dto = lambdaQuery()
                .like(UserEntity::getAccount, anyKeyword)
                .or()
                .like(UserEntity::getName, anyKeyword)
                .list();
        List<UserDto> dtos = dto.stream().map(element -> {
            UserDto d = new UserDto();
            BeanUtils.copyProperties(element, d);
            return d;
        }).collect(Collectors.toList());
        return Result.success(dtos);
    }

    @Override
    public Result<UserReset> ResetPassword(UserReset Ur) {
        UserEntity user = lambdaQuery().eq(UserEntity::getAccount, Ur.getAccount()).one();
        if (user == null) {
            return Result.error(402, "用户不存在");
        }
        if (!passwordEncoder.matches(Ur.getPassword(), user.getPassword())) {
            return Result.error(403, "用户名或密码错误");
        }
        String encodePwd = passwordEncoder.encode(Ur.getNewone());
        lambdaUpdate()
                .eq(UserEntity::getAccount, Ur.getAccount())
                .set(UserEntity::getPassword, encodePwd)
                .update();
        return Result.success(Ur);
    }


}
