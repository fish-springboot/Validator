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

public class UserAopControllerTest extends ValidatorApplicationTests {
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
                MockMvcRequestBuilders.post("/aop/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is200);
    }

    @Test
    public void createUser2() throws Exception{
        user.setName("xx");
        user.setIsMale(null);
        ResultMatcher is404 = MockMvcResultMatchers.status().is(404);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/aop/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is404);
    }
}