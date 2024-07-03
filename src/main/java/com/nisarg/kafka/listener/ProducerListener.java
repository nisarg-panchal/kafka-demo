package com.nisarg.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.kafka.core.ProducerFactory.Listener;

@Slf4j
public class ProducerListener implements Listener {
    @Override
    public void producerAdded(String id, Producer producer) {
        Listener.super.producerAdded(id, producer);
    }

    @Override
    public void producerRemoved(String id, Producer producer) {
        Listener.super.producerRemoved(id, producer);
    }
}
