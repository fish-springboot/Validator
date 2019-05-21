package com.github.fish56.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class ValidatorApplication {
    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;

    public static void main(String[] args) {
        SpringApplication.run(ValidatorApplication.class, args);
    }
}
