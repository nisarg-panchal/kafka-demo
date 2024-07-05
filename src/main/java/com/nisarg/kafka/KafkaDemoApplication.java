package com.nisarg.kafka;

import com.nisarg.kafka.bean.Company;
import com.nisarg.kafka.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class KafkaDemoApplication {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApplication.class, args);
    }

    @Bean
    public KafkaAdmin.NewTopics topics456() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("defaultBoth")
                        .build(),
                TopicBuilder.name("peopleTopic")
                        .replicas(1)
                        .build(),
                TopicBuilder.name("companyTopic")
                        .partitions(3)
                        .build());
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = producerConfigs();
        return new KafkaAdmin(configs);
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(AdminClientConfig.ENABLE_METRICS_PUSH_CONFIG, true);
        configs.put(AdminClientConfig.ENABLE_METRICS_PUSH_DOC, true);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configs;
    }

    @Bean
    public NewTopic topic() {
        log.info("Creating topic!");
        return TopicBuilder.name("demoTopic")
                .partitions(10)
                .replicas(2)
                .build();
    }

    @KafkaListener(id = "myId", topics = {"peopleTopic", "companyTopic"})
    public void listen(String in) {
        log.info("Received message: {}", in);
    }

    /*@Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return _ -> template.send("demoTopic", "If the dream is BIG enough, facts don't count");
    }*/

    @Bean
    public ProducerFactory<String, Person> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Person> kafkaTemplatePerson() {
        KafkaTemplate<String, Person> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setDefaultTopic("peopleTopic");
        return kafkaTemplate;
    }

    @Bean
    public KafkaTemplate<String, Company> kafkaTemplateCompany() {
        KafkaTemplate<String, Company> kafkaTemplate = new KafkaTemplate<>(
                new DefaultKafkaProducerFactory<>(producerConfigs()),
                Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class));
        kafkaTemplate.setDefaultTopic("companyTopic");
        return kafkaTemplate;
    }


}
