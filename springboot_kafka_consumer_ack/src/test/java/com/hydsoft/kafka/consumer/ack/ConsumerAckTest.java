package com.hydsoft.kafka.consumer.ack;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @title: ComsumerAckTest
 * @Description:
 * @Author Jane
 * @Date: 2022/6/27 11:48
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerAckTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testAckModel() {
        ProducerRecord producerRecord = new ProducerRecord("topic.consumer.ack", "this is four test message about kafka consumer ack model of topic.consumer.ack");
        kafkaTemplate.send(producerRecord);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

        }
    }
}
