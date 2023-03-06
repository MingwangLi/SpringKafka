package com.hydsoft.kafka.test;

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
 * @title: DemoTopicTest
 * @Description:
 * @Author Jane
 * @Date: 2022/6/24 10:24
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTopicTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * @Description: 测试往topic.demo.one这个topic发送消息 需要配置 spring.kafka.listener.missing-topics-fatal=false topic不存在时自动创建
     * @Author Jane
     * @Date: 2022/6/24 10:32
     * @Version 1.0
     */
    @Test
    public void test01() {
        logger.info("begin to send message to topic:{}", "topic.demo.one");
        kafkaTemplate.send("topic.demo.one", "This is a test message from kafka in topic topic.demo.one");
        logger.info("send message to topic :{} success", "topic.demo.one");
        //由于消息的发送和消费是异步的 这里让线程休眠是为了测试消息消费
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * @Description: 测试群组下多个消费者消费
     * @Author Jane
     * @Date: 2022/6/24 11:36
     * @Version 1.0
     */
    // @Test
    // public void test02() {
    //     for(int i = 0; i < 10; i++) {
    //         kafkaTemplate.send("topic.demo.two", "This is " + i + " test message from kafka in topic topic.demo.two");
    //     }
    //     try {
    //         Thread.sleep(5000);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * @Description: 测试群组下多个消费者消费(消费者>分区数)
     * @Author Jane
     * @Date: 2022/6/24 11:37
     * @Version 1.0
     */
    @Test
    public void test03() {
        for(int i = 0; i < 10; i++) {
            //将消息发送到两个分区
            // if (i % 2 == 0) {
            //     kafkaTemplate.send("topic.demo.three", 0, null ,"This is " + i + " test message from kafka in topic topic.demo.three");
            // } else {
            //     kafkaTemplate.send("topic.demo.three", 1, null ,"This is " + i + " test message from kafka in topic topic.demo.three");
            // }
            // 默认会均分到两个分区
            kafkaTemplate.send("topic.demo.three", "This is " + i + " test message from kafka in topic topic.demo.three");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * @Description: 测试不同消费组消费消息
     * @Author Jane
     * @Date: 2022/6/24 14:53
     * @Version 1.0
     */
    @Test
    public void test04() {
        for(int i = 0; i < 10; i++) {
            kafkaTemplate.send("topic.demo.four", "This is " + i + " test message from kafka in topic topic.demo.four");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * @Description: 测试新消费组订阅topic  spring.kafka.consumer.auto-offset-reset=earliest时，新消费组从头开始消费
     * @Author Jane
     * @Date: 2022/6/24 15:01
     * @Version 1.0
     */
    @Test
    public void test04_3() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * @Description: 测试使用ProducerRecord ConsumerRecode
     * ProducerRecord 可以结合发送消息回调ProducerListener 对成功和失败做业务处理
     * ConsumerRecode 可以获取消息topic partition offset createTime value等等
     * @Author Jane
     * @Date: 2022/6/24 15:19
     * @Version 1.0
     */
    @Test
    public void test05() {
        ProducerRecord producerRecord = new ProducerRecord("topic.demo.five", "This is a test message from kafka in topic topic.demo.five");
        kafkaTemplate.send(producerRecord);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }

}
