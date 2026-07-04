package com.example.for_testdemo1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.for_testdemo1.Common.Result;
import com.example.for_testdemo1.Dto.UserRegisterDto;
import com.example.for_testdemo1.Entity.UserEntity;
import com.example.for_testdemo1.Service.UserService;
import com.example.for_testdemo1.Vo.UserLoginVo;
import com.example.for_testdemo1.Vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<UserRegisterDto> userregister(@RequestBody UserRegisterDto dto) {
        return userService.userRegister(dto);
    }

    @PostMapping("/login")
    public Result<UserLoginVo> UserLoginVo(@RequestBody UserLoginVo vo) {
        return userService.userLogin(vo);
    }

    @GetMapping("/users")
    public Result<List<UserVo>> list() {
        List<UserEntity> entities = userService.list();
        List<UserVo> vos = entities.stream()
                .map(e -> {
                    UserVo vo = new UserVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                })
                .collect(Collectors.toList());
        return Result.success(vos);
    }

    @GetMapping("/Test/{msg}")
    public Result<String> test(@PathVariable String msg) {
        return Result.unauthorized(msg);
    }

    @GetMapping("/users/{id}")
    public Result<UserVo> getUserById(@PathVariable int id) {
        UserEntity user = userService.getById(id);
        return user == null ? Result.error(404, "用户不存在") :userService.userInfo(id);
    }

    @GetMapping("/users/page")
    public Result<Page<UserEntity>> Page(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size) {
        if (current <= 0 || size <= 0) {
            return Result.error(400, "参数非法，请输入大于零的数");
        }
        return Result.success(userService.page(new Page<>(current, size)));
    }

    @PostMapping("/users")
    public Result<UserEntity> addUser(@RequestBody UserEntity userEntity) {
        userService.save(userEntity);
        return Result.success(userEntity);
    }

    @PutMapping("/users/{id}")
    public Result<UserEntity> updataUser(@PathVariable int id, @RequestBody UserEntity userEntity) {
        userEntity.setId(id);
        userService.updateById(userEntity);
        return Result.success(userEntity);
    }

    @DeleteMapping("/users/{id}")
    public Result<UserEntity> deleteUser(@PathVariable int id) {
        UserEntity user = userService.getById(id);
        if (user != null) {
            userService.removeById(id);
        }
        return user != null ? Result.success() : Result.error(404, "用户不存在");
    }

    @PatchMapping("/users/{id}")
    public Result<UserEntity> updateUser(@PathVariable int id, @RequestBody UserEntity userEntity) {
        userEntity.setId(id);
        userService.updateById(userEntity);
        return Result.success(userEntity);
    }

}
