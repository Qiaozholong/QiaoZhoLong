package com.example.for_testdemo1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserLoginDto;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<UserEntity> register(@RequestBody UserRegisterDto dto) {
        return userService.register(dto);
    }
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginDto dto) {
        return userService.login(dto);
    }

    @GetMapping("/users")
    public Result<List<UserEntity>> list(){
        return  Result.success(userService.list());}

    @GetMapping("/Test/{msg}")
    public Result<String> test(@PathVariable String msg){
        return  Result.unauthorized(msg);
    }

    @GetMapping("/users/{id}")
    public Result<UserEntity> getUserById(@PathVariable int id){
        UserEntity user = userService.getById(id);
        return user==null?Result.error(404,"用户不存在"):Result.success(user);
    }
    @GetMapping("/users/page")
    public Result<Page<UserEntity>> Page(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue="10") int size){
        if(current<=0||size<=0){
            return Result.error(400,"参数非法，请输入大于零的数");
        }
        return Result.success(userService.page(new Page<>(current,size)));
    }
    @PostMapping("/users")
    public Result<UserEntity> addUser(@RequestBody UserEntity userEntity){
        userService.save(userEntity);
        return Result.success(userEntity);
    }
    @PutMapping("/users/{id}")
    public Result<UserEntity> updataUser(@PathVariable int id,@RequestBody UserEntity userEntity){
        userEntity.setId(id);
        userService.updateById(userEntity);
        return Result.success(userEntity);
    }
    @DeleteMapping("/users/{id}")
    public Result<UserEntity> deleteUser(@PathVariable int id) {
        UserEntity user = userService.getById(id);
        if(user!=null){userService.removeById(id);}
        return user != null? Result.success(): Result.error(404, "用户不存在");
    }
    @PatchMapping("/users/{id}")
    public Result<UserEntity> updateUser(@PathVariable int id,@RequestBody UserEntity userEntity){
        userEntity.setId(id);
        userService.updateById(userEntity);
        return Result.success(userEntity);
    }

}
