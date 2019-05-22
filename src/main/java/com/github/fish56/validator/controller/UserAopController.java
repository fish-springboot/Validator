package com.github.fish56.validator.controller;

import com.github.fish56.validator.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 不需要做任何修改，AOP可以实现非侵入式扩展
 */
@RestController
@RequestMapping("/aop/users")
public class UserAopController {
    @PostMapping
    public Object createUser(@RequestBody User user){
        return user;
    }
}
