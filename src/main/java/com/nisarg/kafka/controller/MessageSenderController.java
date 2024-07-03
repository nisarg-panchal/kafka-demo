package com.nisarg.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/Sender")
public class MessageSenderController {
    @GetMapping
    public String send(String message) {
        log.info("Sending message:{}", message);
        return message;
    }
}
