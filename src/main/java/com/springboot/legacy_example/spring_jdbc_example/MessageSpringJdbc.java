package com.springboot.legacy_example.spring_jdbc_example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
public class MessageSpringJdbc {
    @Id
    private Long messageId;
    private String message;
}
