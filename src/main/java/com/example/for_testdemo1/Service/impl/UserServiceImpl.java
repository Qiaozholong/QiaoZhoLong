package com.example.for_testdemo1.Service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.for_testdemo1.Common.BusinessException;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Config.JwtUtil;
import com.example.for_testdemo1.Dto.*;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Mapper.UserMapper;
import com.example.for_testdemo1.Service.UserService;
import com.example.for_testdemo1.Util.RoleGetter_convertLIst;
import com.example.for_testdemo1.Vo.LoginResultVo;
import com.example.for_testdemo1.Vo.UserDetailVo;
import com.example.for_testdemo1.Vo.UserVo;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    Map<String, String> FIELD_CN = Map.of(
            "email", "邮箱",
            "gender", "性别",
            "name", "用户名"
    );
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(PasswordEncoder passwordencoder, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordencoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Result<LoginResultVo> userRegister(RegisterDto dto) {
        UserEntity exist = lambdaQuery().eq(UserEntity::getAccount, dto.getAccount()).one();
        if (exist != null) {
            throw new BusinessException(400, "账号已存在");
        }
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        save(user);
        LoginResultVo result = new LoginResultVo();
        BeanUtils.copyProperties(user, result);
        String token = jwtUtil.generateToken(user.getId(), user.getAccount(), user.getRole());
        result.setToken(token);
        return Result.success(result);
    }

    @Override
    public Result<LoginResultVo> userLogin(LoginDto dto) {
        UserEntity user = lambdaQuery().eq(UserEntity::getAccount, dto.getAccount()).one();
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "账号或密码错误");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getAccount(), user.getRole());
        LoginResultVo vo = new LoginResultVo();
        BeanUtils.copyProperties(user, vo);
        vo.setToken(token);
        return Result.success(vo);
    }

    @Override
    public Result<UserVo> userInfo(int id) {
        UserEntity user = getById(id);
        if (user == null) {
            throw new BusinessException(400, "用户不存在");
        }
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        return Result.success(vo);
    }

    @Override
    public Result<List<UserVo>> userInfo() {
        List<UserEntity> Ue = list();
        List<UserVo> vo = RoleGetter_convertLIst.convertList_U(Ue, UserVo.class);
        return Result.success(vo);
    }

    @Override
    public Result<UserEntity> userAllInfo(int id) {
        UserEntity user = getById(id);
        if (user == null) {
            throw new BusinessException(400, "用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }

    @Override
    public Result<List<UserDetailVo>> userAllInfo() {
        List<UserEntity> Ue = list();
        List<UserDetailVo> vo = RoleGetter_convertLIst.convertList_U(Ue, UserDetailVo.class);
        return Result.success(vo);
    }

    @Override
    public Result<Page<UserVo>> page(int current) {
        Page<UserEntity> entityPage = page(new Page<>(current, 10));
        Page<UserVo> voPage = new Page<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());
        voPage.setRecords(RoleGetter_convertLIst.convertList_U(entityPage.getRecords(), UserVo.class));
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
    public Result<UserResetDto> ResetPassword(UserResetDto Ur) {
        UserEntity user = lambdaQuery().eq(UserEntity::getAccount, Ur.getAccount()).one();
        if (user == null || !passwordEncoder.matches(Ur.getPassword(), user.getPassword())) {
            throw new BusinessException(402, "用户名或密码错误");
        }
        String encodePwd = passwordEncoder.encode(Ur.getNewone());
        lambdaUpdate()
                .eq(UserEntity::getAccount, Ur.getAccount())
                .set(UserEntity::getPassword, encodePwd)
                .update();
        return Result.success(Ur);
    }

    @Override
    public Result<Void> deleteUser(int id) {
        UserEntity user = getById(id);
        if (user != null) {
            removeById(id);
            return Result.success();
        }
        throw new BusinessException(400, "用户不存在");
    }

    @Override
    public String getDetail(int id) {
        UserEntity user = getById(id);
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        List<String> nullFields = new ArrayList<>();
        for (Field field : UserEntity.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(user);
                if (value == null) {
                    nullFields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                throw new BusinessException(402, "字段访问异常");
            }
        }
        if (nullFields.isEmpty()) {
            return "信息完整";
        }
        return nullFields.stream()
                .map(e -> FIELD_CN.getOrDefault(e, e) + "未设置")
                .collect(Collectors.joining(",\n"));
    }

    @Override
    public void setDetail(PatchDto dto, int id) {
        UserEntity user = getById(id);
        user.setEmail(dto.getEmail());
        user.setGender(dto.getGender());
        updateById(user);
    }


}
