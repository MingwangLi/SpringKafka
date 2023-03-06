package com.hydsoft.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @title: TopicConfiguration
 * @Description:
 * @Author Jane
 * @Date: 2022/6/24 11:04
 * @Version 1.0
 */
@Configuration
public class TopicConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Description: 创建一个 2个分区 1个副本的topic topic.demo.two
     * @Author Jane
     * @Date: 2022/6/24 11:05
     * @Version 1.0
     */
    // @Bean
    // public NewTopic demoTwoTopic() {
    //     logger.info("create topic:{} ", "topic.demo.two");
    //     return new NewTopic("topic.demo.two", 2, (short)1);
    // }

    /**
     * @Description: 创建一个 2个分区 1个副本的topic topic.demo.three
     * @Author Jane
     * @Date: 2022/6/24 11:05
     * @Version 1.0
     */
    @Bean
    public NewTopic demoThreeTopic() {
        logger.info("create topic:{} ", "topic.demo.three");
        return new NewTopic("topic.demo.three", 2, (short)1);
    }
}
