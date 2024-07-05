package com.nisarg.kafka.listener;

import com.nisarg.kafka.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PeopleProducerListener implements ProducerListener<String, Person> {
    @Override
    public void onSuccess(ProducerRecord<String, Person> producerRecord, RecordMetadata recordMetadata) {
        ProducerListener.super.onSuccess(producerRecord, recordMetadata);
        log.info("Successfully sent:{}", producerRecord.value());
    }

    @Override
    public void onError(ProducerRecord<String, Person> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        ProducerListener.super.onError(producerRecord, recordMetadata, exception);
        log.error("Failed to send:{}", producerRecord.value());
    }
}
