package com.nisarg.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.core.ConsumerFactory.Listener;

@Slf4j
public class ConsumerListener implements Listener {
    @Override
    public void consumerAdded(String id, Consumer consumer) {
        Listener.super.consumerAdded(id, consumer);
    }

    @Override
    public void consumerRemoved(String id, Consumer consumer) {
        Listener.super.consumerRemoved(id, consumer);
    }
}
