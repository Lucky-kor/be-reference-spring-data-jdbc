package com.springboot.legacy_example.jdbc_example;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageJdbcMapper {
    MessageJdbc messageDtoToMessage(MessageJdbcPostDto messagePostDto);
    MessageJdbcResponseDto messageToMessageResponseDto(MessageJdbc message);
}
