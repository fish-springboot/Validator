package com.github.fish56.validator.controller;

import com.github.fish56.validator.aop.ShouldValidate;
import com.github.fish56.validator.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Past;

/**
 * 使用自定义注解来验证
 */
@RestController
@RequestMapping("/aop/annotation/users")
public class UserAopValidateAnnotationController {

    @PostMapping
    public Object createUser(@ShouldValidate @RequestBody User user){
        return user;
    }

    @PatchMapping
    public Object createUser2(@RequestBody User user){
        return user;
    }
}
