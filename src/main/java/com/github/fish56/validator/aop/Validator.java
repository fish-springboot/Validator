package com.github.fish56.validator.aop;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;

/**
 * aop的方式可以拿到Controller方法的参数
 * 这是filter和interceptor所不具备的能力
 *
 * 这里我们拦截了前往Controller的请求，手动的进行的参数的校验。
 *
 * 而直接在Controller层进行valida有两大缺陷
 *   - 侵入式强，需要Controller层多一个BindingResult作为参数
 *   - 大量的样板代码
 *
 * 这里我们通过AOP技术拦截Controller，统一的进行参数校验以及异常处理
 * 大大的节省了代码量并提高的扩展性
 */
@Slf4j
@Aspect
@Component
public class Validator {
    private static javax.validation.Validator javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private static SpringValidatorAdapter validator =  new SpringValidatorAdapter(javaxValidator);

    /**
     * 这个切面是强制校验所有的参数的合法性
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.github.fish56.validator.controller.UserAopController.createUser(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("正在通过AOP验证字段的合法性");

        // 同来存储校验结果
        StringBuilder errorMessage = new StringBuilder();

        // 获得方法的参数
        Object[] args = joinPoint.getArgs();

        // 一次校验所有的规则，而不是遇到错误就停止
        // 对于没有
        for (int i = 0; i < args.length; i++) {
            Errors errors = new BeanPropertyBindingResult(args[i], args[i].getClass().getName());
            validator.validate(args[i], errors);

            // 这样格式美观一点
            if (errors.hasErrors()){
                errors.getAllErrors().forEach((objectError) -> {
                    // 将错误信息输出到errorMessage中
                    errorMessage.append(objectError.getDefaultMessage() + ", ");
                });
            }
        }

        // 有错误的话直接返回
        if (errorMessage.length() > 0) {
            log.warn("字段不符合规则，已经打回去重写了！");

            // 删除最后面的 ", "
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length() - 1);

            return ResponseEntity.status(404).body(errorMessage.toString());
        }

        Object object = joinPoint.proceed();

        log.info("字段符合规则！！");

        return object;
    }

    // 如果想校验所有的被@Valid注解参数的合法性，可以参考
    // https://stackoverflow.com/questions/21275819
}

