package com.springboot.legacy_example.jdbc_example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
public class MessageJdbc {
    @Id
    private Long messageId;
    private String message;
}
