package com.hydsoft.kafka.batch;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @title: BatchMessageTest
 * @Description:
 * @Author Jane
 * @Date: 2022/6/27 9:27
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchMessageTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * @Description: 测试批量小消费
     * @Author Jane
     * @Date: 2022/6/27 10:22
     * @Version 1.0
     */
    @Test
    public void testBeachMessage() {
        for (int i = 0; i < 20; i++) {
            ProducerRecord producerRecord = new ProducerRecord("topic.demo.batch", "the " + i + " test message from kafka of topic.demo.batch");
            kafkaTemplate.send(producerRecord);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }
}
