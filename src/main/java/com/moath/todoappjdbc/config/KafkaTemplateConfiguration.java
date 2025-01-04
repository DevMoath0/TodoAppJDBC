package com.moath.todoappjdbc.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTemplateConfiguration {

    @Bean
    public NewTopic myTopic() {
        return TopicBuilder.name("todo-topic").build();
    }
}
