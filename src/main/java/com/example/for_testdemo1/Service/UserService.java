package com.example.for_testdemo1.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.*;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Vo.LoginResultVo;
import com.example.for_testdemo1.Vo.UserDetailVo;
import com.example.for_testdemo1.Vo.UserVo;

import java.util.List;

public interface UserService extends IService<UserEntity> {
    //注册接口
    Result<LoginResultVo> userRegister(RegisterDto dto);

    //登录接口
    Result<LoginResultVo> userLogin(LoginDto dto);

    //用户单独简要信息查询接口
    Result<UserVo> userInfo(int id);

    //用户总简要信息查询接口
    Result<List<UserVo>> userInfo();

    //用户单独详细信息查询入口
    Result<UserEntity> userAllInfo(int id);

    //用户总详细信息查询入口
    Result<List<UserDetailVo>> userAllInfo();

    //用户界面分页卡入口
    Result<Page<UserVo>> page(int current);

    //用户测试信息注入接口
    Result<List<UserDto>> userSearch(String anyKeyword);

    //用户重置密码接口
    Result<UserResetDto> ResetPassword(UserResetDto Ur);

    //删除账户接口
    Result<Void> deleteUser(int id);
    //查询未设置属性
    String getDetail(int id);
    //查询用户未设置属性
    void setDetail(PatchDto dto,int id);
}
