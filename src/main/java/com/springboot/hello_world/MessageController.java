package com.springboot.hello_world;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/v1/messages")
@RestController
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper mapper;

    public MessageController(MessageService messageService,
                             MessageMapper mapper) {
        this.messageService = messageService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMessages(
            @Valid @RequestBody MessagePostDto messagePostDto) {
        // 사용자의 요청을 받아들입니다.
        Message message = messageService.createMessage(mapper.messageDtoToMessage(messagePostDto));

        return ResponseEntity.ok(mapper.messageToMessageResponseDto(message));
    }
}
