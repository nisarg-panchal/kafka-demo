package com.nisarg.kafka.listener;

import com.nisarg.kafka.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.kafka.core.ProducerFactory.Listener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducerListener implements Listener<String, Person> {
    @Override
    public void producerAdded(String id, Producer<String, Person> producer) {
        Listener.super.producerAdded(id, producer);
        logProducerDetails(id);
    }

    @Override
    public void producerRemoved(String id, Producer<String, Person> producer) {
        Listener.super.producerRemoved(id, producer);
        logProducerDetails(id);
    }

    private static void logProducerDetails(String id) {
        log.info("Producer Added with Id:{}", id);
//        producer.listTopics().values().stream().forEach(o -> log.info("Topics:{}", o.toString()));
    }
}
