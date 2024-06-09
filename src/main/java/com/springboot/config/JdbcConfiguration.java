package com.springboot.config;

import com.springboot.converter.IntegerToMoneyConverter;
import com.springboot.converter.MoneyToIntegerConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Money 타입과 Integer 간의 converter를 추가하기 위한 JdbcCofiguration
 */
@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {
    @Override
    protected List<?> userConverters() {
        return Arrays.asList(new MoneyToIntegerConverter(), new IntegerToMoneyConverter());
    }
}
