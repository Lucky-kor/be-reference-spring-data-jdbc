package com.springboot.legacy_example.jdbc_example;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MessageJdbcPostDto {
    @NotBlank
    private String message;
}
