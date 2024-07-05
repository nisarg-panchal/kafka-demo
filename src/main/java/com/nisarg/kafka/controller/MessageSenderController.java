package com.nisarg.kafka.controller;

import com.nisarg.kafka.bean.Company;
import com.nisarg.kafka.bean.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/Sender")
@AllArgsConstructor
public class MessageSenderController {

    private KafkaTemplate<String, Person> personKafkaTemplate;
    private KafkaTemplate<String, Company> companyKafkaTemplate;

    @PostMapping("/person")
    public Person sendPerson(@RequestBody Person person) {
        log.info("Sending person:{}", person);
        CompletableFuture<SendResult<String, Person>> sendResultCompletableFuture = personKafkaTemplate
                .send("peopleTopic", person);
        sendResultCompletableFuture.whenComplete((stringPersonSendResult, throwable) -> {
            Person personRecord = stringPersonSendResult.getProducerRecord().value();
            log.info("Message (Person) sent with id:{}, name:{}",
                    personRecord.getId(), personRecord.getName());
        });
        return person;
    }

    @PostMapping("/company")
    public Company sendCompany(@RequestBody Company company) {
        log.info("Sending company:{}", company);
        CompletableFuture<SendResult<String, Company>> sendResultCompletableFuture = companyKafkaTemplate
                .send("companyTopic", company);
        sendResultCompletableFuture.whenComplete((stringPersonSendResult, throwable) -> {
            Company companyRecord = stringPersonSendResult.getProducerRecord().value();
            log.info("Message (Company) sent with id:{}, name:{}",
                    companyRecord.getId(), companyRecord.getName());
        });
        return company;
    }
}
