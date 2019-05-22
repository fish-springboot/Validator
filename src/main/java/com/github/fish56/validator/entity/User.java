package com.github.fish56.validator.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * Size: 字符串的长度
 * Length: 字符串的长度
 * NotNull: 不能为null
 * NotEmpty: 不能为空白 比如""
 * Email: 判断是否是字符串
 *
 * message： 出错后的信息
 *
 * 一个大坑：Size只检验字符串的长度，但是允许为null！！！服了
 */
@Data
public class User {
    private Integer id;

    @Size(min = 3, max = 9, message = "名字长度应该在3-9之间")
    private String name;

    @Length(min = 12, max = 200, message = "自我介绍长度应该在12-200之间")
    private String info;

    @NotNull(message = "性别不能为空")
    private Boolean isMale;
    // 善于利用bool类型来提高字段的可读性

    @NotEmpty(message = "公司名字不能为空")
    private String company;

    @Past(message = "你出生在未来？？")
    private Date birthday;

    @Future(message = "你已经死了？？")
    private Date endDay;

    @Email
    private String email;
}
