package com.nisarg.kafka.listener;

import com.nisarg.kafka.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.core.ConsumerFactory.Listener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerListener implements Listener<String, Person> {
    @Override
    public void consumerAdded(String id, Consumer<String, Person> consumer) {
        Listener.super.consumerAdded(id, consumer);
        logConsumerDetails(id, consumer);
    }

    private static void logConsumerDetails(String id, Consumer<String, Person> consumer) {
        log.info("Consumer Added with Id:{}", id);
        consumer.listTopics().values().stream().forEach(o -> log.info("Topics:{}", o.toString()));
    }

    @Override
    public void consumerRemoved(String id, Consumer<String, Person> consumer) {
        Listener.super.consumerRemoved(id, consumer);
        logConsumerDetails(id, consumer);
    }
}
