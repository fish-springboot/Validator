package com.github.fish56.validator.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.validator.ValidatorApplicationTests;
import com.github.fish56.validator.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

public class UserAopValidateAnnotationControllerTest extends ValidatorApplicationTests {
    private static String url = "/aop/annotation/users";
    private User user;

    @Before
    public void setUser(){
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
     * 正常有效的请求
     * @throws Exception
     */
    @Test
    public void createUser() throws Exception{
        ResultMatcher is200 = MockMvcResultMatchers.status().is(200);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is200);
    }

    /**
     * 参数错误导致被被拒绝
     * @throws Exception
     */
    @Test
    public void createUserError() throws Exception{
        user.setName("xx");
        user.setIsMale(null);
        ResultMatcher is400 = MockMvcResultMatchers.status().is(400);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is400);
    }

    /**
     * 对应的Controller没有注解，所以不会进行校验，请求正常执行
     * @throws Exception
     */
    @Test(expected = java.lang.AssertionError.class)
    public void createUser2() throws Exception{
        user.setName("xx");
        user.setIsMale(null);
        ResultMatcher is400 = MockMvcResultMatchers.status().is(400);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is400);
    }
}