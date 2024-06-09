package com.springboot.legacy_example.jdbc_example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/v1/jdbc/messages")
@RestController
public class MessageJdbcController {
    private final MessageJdbcService messageService;
    private final MessageJdbcMapper mapper;

    public MessageJdbcController(MessageJdbcService messageService,
                                 MessageJdbcMapper mapper) {
        this.messageService = messageService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMessages(
            @Valid @RequestBody MessageJdbcPostDto messagePostDto) {
        // 사용자의 요청을 받아들입니다.
        MessageJdbc message =
                messageService.createMessage(mapper.messageDtoToMessage(messagePostDto));

        return ResponseEntity.ok(mapper.messageToMessageResponseDto(message));
    }
}
