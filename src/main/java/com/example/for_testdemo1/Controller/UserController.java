package com.example.for_testdemo1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserDto;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Dto.UserReset;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Service.UserService;
import com.example.for_testdemo1.Vo.UserDetailVo;
import com.example.for_testdemo1.Vo.UserLoginVo;
import com.example.for_testdemo1.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    //构造方法注入
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //注册接口
    @PostMapping("/register")
    public Result<UserRegisterDto> userregister(@RequestBody UserRegisterDto dto) {
        return userService.userRegister(dto);
    }

    //登录接口
    @PostMapping("/login")
    public Result<UserLoginVo> UserLoginVo(@RequestBody UserLoginVo vo) {
        return userService.userLogin(vo);
    }

    //简要查询接口
    @GetMapping("/getall")
    public Result<List<UserVo>> getAll() {
        return userService.userInfo();
    }

    //单独查询用户简要信息接口
    @GetMapping("/{id}")
    public Result<UserVo> getUserInfo(@PathVariable int id) {
        return userService.userInfo(id);
    }

    //单独查询用户详细信息接口
    @GetMapping("/get/{id}")
    public Result<UserEntity> getUserAllInfo(@PathVariable int id) {
        return userService.userAllInfo(id);
    }

    @GetMapping("/get")
    public Result<List<UserDetailVo>> getUserAllInfo() {
        return userService.userAllInfo();
    }

    //分页接口
    @GetMapping("/page")
    public Result<Page<UserVo>> Page(@RequestParam(defaultValue = "1") int current) {
        return userService.page(current);
    }

    //模糊查询接口
    @GetMapping("/search")
    public Result<List<UserDto>> userSearch(
            @RequestParam String search) {
        return userService.userSearch(search);
    }

    //重置密码接口
    @PatchMapping("/reset/{id}")
    public Result<UserReset> resetPassword(
            @RequestBody UserReset Ur
    ) {
        return userService.ResetPassword(Ur);
    }

    @DeleteMapping("/{id}")
    public Result<UserEntity> deleteUser(@PathVariable int id) {
        UserEntity user = userService.getById(id);
        if (user != null) {
            userService.removeById(id);
        }
        return user != null ? Result.success() : Result.error(404, "用户不存在");
    }


    //字符返回测试接口
    @GetMapping("/Test/{msg}")
    public Result<String> test(@PathVariable String msg) {
        return Result.unauthorized(msg);
    }

}
