package com.springboot.legacy_example.jdbc_example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;

@Slf4j
@Service
public class MessageJdbcService {
    private final String DRIVER_NAME = "org.h2.Driver";
    private final String URL = "jdbc:h2:mem:test";
    private final String username = "sa";
    private final String password = "";

    private Connection connection;
    private PreparedStatement statement;

    // JDBC API 사용 예(저수준 API)
    public MessageJdbc createMessage(MessageJdbc message) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, username, password);
            String sql = "INSERT INTO MESSAGE (message) VALUES (?)";

            statement = connection.prepareStatement(sql);
            statement.setString(1, message.getMessage());
            statement.executeUpdate();
            return new MessageJdbc(null, message.getMessage());
        } catch (SQLException e) {
            log.error("SQLException happened: ", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
