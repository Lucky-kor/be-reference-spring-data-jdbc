package com.springboot.legacy_example.spring_jdbc_example;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MessageSpringJdbcPostDto {
    @NotBlank
    private String message;
}
