package com.github.fish56.validator.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {
    private static SpringValidatorAdapter validator;

    private User user;

    /**
     * 配置validator
     */
    @BeforeClass
    public static void initValidator(){
        Validator javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
        // Spring 的validator的适配器
        validator = new SpringValidatorAdapter(javaxValidator);
    }

    /**
     * 每次测试前，初始化一个完好的user
     */
    @Before
    public void resetUser(){
        user = new User();
        user.setId(2);
        user.setName("Jon");
        user.setBirthday(new Date(new Date().getTime() - 100000));
        user.setCompany("大唐理事会");
        user.setEmail("bitfishxyz@gamil.com");
        user.setInfo("I not know thing");
        user.setIsMale(true);
    }

    /**
     * 初始值是个符合规则的对象
     */
    @Test
    public void isOk(){
        Errors errors = new BeanPropertyBindingResult(user, user.getClass().getName());
        validator.validate(user, errors);
        assertTrue(errors.getAllErrors().isEmpty());
    }

    @Test
    public void size() {
        user.setName("tj");
        Errors errors = new BeanPropertyBindingResult(user, user.getClass().getName());

        validator.validate(user, errors);

        List<ObjectError> errorList = errors.getAllErrors();
        for (ObjectError error : errorList) {
            System.out.println(error.getDefaultMessage());
        }
        // 名字长度应该在3-9之间
    }

    @Test
    public void length() {
        user.setInfo("I know it!");
        Errors errors = new BeanPropertyBindingResult(user, user.getClass().getName());

        validator.validate(user, errors);
        System.out.println(errors.getAllErrors());
    }

    @Test
    public void notNull(){
        user.setIsMale(null);
        Errors errors = new BeanPropertyBindingResult(user, user.getClass().getName());

        validator.validate(user, errors);

        List<ObjectError> errorList = errors.getAllErrors();
        for (ObjectError error : errorList) {
            System.out.println(error.getDefaultMessage());
        }
        // 性别不能为空
    }

}