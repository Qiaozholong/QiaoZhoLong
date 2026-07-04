package com.example.for_testdemo1.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Vo.UserLoginVo;
import com.example.for_testdemo1.Vo.UserVo;

public interface UserService extends IService<UserEntity> {
    Result<UserRegisterDto> userRegister(UserRegisterDto dto);
    Result<UserLoginVo> userLogin(UserLoginVo vo);
    Result<UserVo>  userInfo(int id);

}
