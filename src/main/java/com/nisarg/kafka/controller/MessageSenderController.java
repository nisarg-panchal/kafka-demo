package com.nisarg.kafka.controller;

import com.nisarg.kafka.bean.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/Sender")
@AllArgsConstructor
public class MessageSenderController {

    private KafkaTemplate<String, Person> template;

    @PostMapping
    public Person send(@RequestBody Person person) {
        Message<Person> message = new GenericMessage<>(person);
        log.info("Sending person in message:{}", message);
        template.send(message);
        return person;
    }
}
