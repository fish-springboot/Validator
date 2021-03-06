package com.github.fish56.validator.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.validator.ValidatorApplicationTests;
import com.github.fish56.validator.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.Assert.*;

public class UserControllerTest extends ValidatorApplicationTests {
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

    @Test
    public void createUser() throws Exception{
        ResultMatcher is200 = MockMvcResultMatchers.status().is(200);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is200);
    }

    @Test
    public void createUserErrorNameSize() throws Exception{
        user.setName("xx");
        ResultMatcher is404 = MockMvcResultMatchers.status().is(400);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is404);
    }

    /**
     * 一个大坑：Size只检验字符串的长度，但是允许为null！！！服了
     * 所以下面的测试会返回200成功，Controller运行执行！！
     * @throws Exception
     */
    @Test(expected = java.lang.AssertionError.class)
    public void createUser3() throws Exception{
        user.setName(null);
        ResultMatcher is400 = MockMvcResultMatchers.status().is(400);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is400);
    }
}