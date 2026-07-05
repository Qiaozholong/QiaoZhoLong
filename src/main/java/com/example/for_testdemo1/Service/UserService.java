package com.example.for_testdemo1.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserDto;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Dto.UserReset;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Vo.UserDetailVo;
import com.example.for_testdemo1.Vo.UserLoginVo;
import com.example.for_testdemo1.Vo.UserVo;

import java.util.List;

public interface UserService extends IService<UserEntity> {
    //注册接口
    Result<UserRegisterDto> userRegister(UserRegisterDto dto);
    //登录接口
    Result<UserLoginVo> userLogin(UserLoginVo vo);
    //用户单独简要信息查询接口
    Result<UserVo>  userInfo(int id);
    //用户总简要信息查询接口
    Result<List<UserVo>>  userInfo();
    //用户单独详细信息查询入口
    Result<UserEntity>  userAllInfo(int id);
    //用户总详细信息查询入口
    Result<List<UserDetailVo>> userAllInfo();
    //用户界面分页卡入口
    Result<Page<UserVo>> page(int current);
    //用户测试信息注入接口
    Result<List<UserDto>> userSearch(String anyKeyword);
    //用户重置密码接口
    Result<UserReset> ResetPassword(UserReset Ur);

}
