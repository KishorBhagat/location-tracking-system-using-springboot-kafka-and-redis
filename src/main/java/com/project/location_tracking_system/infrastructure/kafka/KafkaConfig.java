package com.project.location_tracking_system.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic userLocationTopic() {
        return new NewTopic(
                KafkaTopics.USER_LOCATION_EVENTS,
                3,
                (short) 1
        );
    }
}
