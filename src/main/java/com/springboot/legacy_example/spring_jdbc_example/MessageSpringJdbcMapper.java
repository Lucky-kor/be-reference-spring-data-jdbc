package com.springboot.legacy_example.spring_jdbc_example;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageSpringJdbcMapper {
    MessageSpringJdbc messageDtoToMessage(MessageSpringJdbcPostDto messagePostDto);
    MessageSpringJdbcResponseDto messageToMessageResponseDto(MessageSpringJdbc message);
}
