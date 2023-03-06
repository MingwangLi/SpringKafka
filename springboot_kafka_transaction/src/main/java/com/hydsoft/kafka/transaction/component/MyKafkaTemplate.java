package com.hydsoft.kafka.transaction.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title: MyKafkaTemplate
 * @Description:
 * @Author Jane
 * @Date: 2022/6/24 15:56
 * @Version 1.0
 */
@Component
public class MyKafkaTemplate {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Transactional
    public void testTransaction() {
        kafkaTemplate.send("topic.demo.transaction01", "This is another test message about transaction in topic.demo.transaction01");
        throw new RuntimeException();
    }
}
