package com.training.restaurant;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
//@Profile("dev")
public class AutoConfig {

    @Bean
    public NewTopic orderEvents(){
        return TopicBuilder.name("order-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
