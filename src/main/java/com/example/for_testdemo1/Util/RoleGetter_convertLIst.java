package com.example.for_testdemo1.Util;

import com.example.for_testdemo1.Common.BusinessException;
import com.example.for_testdemo1.Entity.ArticleEntity;
import com.example.for_testdemo1.Entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RoleGetter_convertLIst {
    //查询jwt令牌中的user,role字段判断权限
    public static void checkPermission(HttpServletRequest request, int id) {
        int userId = (int) request.getAttribute("userId");
        int userRole = (int) request.getAttribute("role");
        if (id != userId && userRole != 1) {
            throw new BusinessException(403, "无权访问该数据信息");
        }
    }
    //同上，但只有role字段
    public static void checkRole(HttpServletRequest request) {
        int userRole = (int) request.getAttribute("role");
        if (userRole != 1) {
            throw new BusinessException(403, "无权访问该数据信息");
        }
    }
    public static <T> List<T> convertList_U(List<UserEntity> entities, Class<T> targetClass) {
    return entities.stream()
            .map(element->{
                try{
                    T t=targetClass.getDeclaredConstructor().newInstance();
                    BeanUtils.copyProperties(element,t);
                    return t;
                }catch(Exception e){
                    throw  new BusinessException(500, "数据转换失败: " + e.getMessage());
                }
            }).collect(Collectors.toList());
    }
    public static <T> List<T> convertList_A(List< ArticleEntity >entities,Class<T> targetClass){
        return entities.stream()
                .map(element->{
                    try{
                        T t=targetClass.getDeclaredConstructor().newInstance();
                        BeanUtils.copyProperties(element,t);
                        return t;
                    }catch(Exception e){
                        throw new BusinessException(500,"数据转换失败"+e.getMessage());
                    }
                }).collect(Collectors.toList());
    }

}
