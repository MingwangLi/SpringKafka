package com.hydsoft.kafka.batch.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;

/**
 * @title: BatchTopicConfiguration
 * @Description:
 * @Author Jane
 * @Date: 2022/6/27 9:30
 * @Version 1.0
 */
@Configuration
public class BatchTopicConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Bean
    public NewTopic newTopic() {
        logger.info("create topic:{} ", "topic.demo.batch");
        return new NewTopic("topic.demo.batch", 2, (short)1);
    }
}
