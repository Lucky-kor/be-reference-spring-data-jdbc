package com.springboot.legacy_example.spring_jdbc_example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageSpringJdbcService {
    private final String DRIVER_NAME = "org.h2.Driver";
    private final String URL = "jdbc:h2:mem:test";
    private final String username = "sa";
    private final String password = "";

    // 템플릿/콜백 패턴
    private final JdbcTemplate jdbcTemplate;

    public MessageSpringJdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Spring JDBC
    public MessageSpringJdbc createMessage(MessageSpringJdbc message) {
       String sql = "INSERT INTO MESSAGE (message) VALUES (?)";
        int key = jdbcTemplate.update(sql, message.getMessage());

       return new MessageSpringJdbc(null, message.getMessage());
    }
}
