package com.springboot.legacy_example.spring_jdbc_example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/v1/spring-jdbc/messages")
@RestController
public class MessageSpringJdbcController {
    private final MessageSpringJdbcService messageService;
    private final MessageSpringJdbcMapper mapper;

    public MessageSpringJdbcController(MessageSpringJdbcService messageService,
                                       MessageSpringJdbcMapper mapper) {
        this.messageService = messageService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMessages(
            @Valid @RequestBody MessageSpringJdbcPostDto messagePostDto) {
        // 사용자의 요청을 받아들입니다.
        MessageSpringJdbc message =
                messageService.createMessage(mapper.messageDtoToMessage(messagePostDto));

        return ResponseEntity.ok(mapper.messageToMessageResponseDto(message));
    }
}
