package com.github.fish56.validator.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.fish56.validator.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 通过@Valid注解来开启参数校验
 * 需要后面跟一个BindingResult来接受校验结果
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public Object createUser(@Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(JSONObject.toJSONString(
                            bindingResult.getAllErrors()));
        }
        return user;
    }
}
