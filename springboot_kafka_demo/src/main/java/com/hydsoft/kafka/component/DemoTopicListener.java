package com.hydsoft.kafka.component;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @title: DemoTopicListener
 * @Description:
 * @Author Jane
 * @Date: 2022/6/24 10:23
 * @Version 1.0
 */
@Component
public class DemoTopicListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Description: 只配置topic 默认会使用application.properties consumer 相关配置
     * @Author Jane
     * @Date: 2022/6/24 10:31
     * @Version 1.0
     */
    @KafkaListener(topics = "topic.demo.one")
    public void listener01(String message) {
        logger.info("receive message:{}", message);
    }

    /**
     * @Description: 测试同一群组消费topic
     * 运行日志
     * topic.demo.two 有两个Partition 这里配置同一个groupId下的两个id 相当于一个id配置concurrency=2
     *
     * 2022-06-24 11:23:54.373  INFO 4496 --- [        2-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : group02: partitions assigned: [topic.demo.two-1]
     * 2022-06-24 11:23:54.373  INFO 4496 --- [        1-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : group02: partitions assigned: [topic.demo.two-0]
     *
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        1-0-C-1] c.h.kafka.component.DemoTopicListener    : 1 receive message:This is 0 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        2-0-C-1] c.h.kafka.component.DemoTopicListener    : 2 receive message:This is 1 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        1-0-C-1] c.h.kafka.component.DemoTopicListener    : 1 receive message:This is 2 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        2-0-C-1] c.h.kafka.component.DemoTopicListener    : 2 receive message:This is 3 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        1-0-C-1] c.h.kafka.component.DemoTopicListener    : 1 receive message:This is 4 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        2-0-C-1] c.h.kafka.component.DemoTopicListener    : 2 receive message:This is 5 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        1-0-C-1] c.h.kafka.component.DemoTopicListener    : 1 receive message:This is 6 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        1-0-C-1] c.h.kafka.component.DemoTopicListener    : 1 receive message:This is 8 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        2-0-C-1] c.h.kafka.component.DemoTopicListener    : 2 receive message:This is 7 test message from kafka in topic topic.demo.two
     * 2022-06-24 11:23:54.676  INFO 4496 --- [        2-0-C-1] c.h.kafka.component.DemoTopicListener    : 2 receive message:This is 9 test message from kafka in topic topic.demo.two
     *
     * topic的每一个partition只能被同一个group的一个线程消费  这个是kafka很重要的特点 设置concurrency时，注意同一个group(即使id不同)的concurrency之和不能大于partitions 否则多余的消费者无法消费消息
     * 避免多线程锁消费同一个消息锁竞争，提高吞吐量。
     * @Author Jane
     * @Date: 2022/6/24 10:58
     * @Version 1.0
     */
    // @KafkaListener(id = "1", groupId = "group02", topics = "topic.demo.two")
    // public void listener02_1(String message) {
    //     logger.info("1 receive message:{}", message);
    // }
    //
    // @KafkaListener(id = "2", groupId = "group02", topics = "topic.demo.two")
    // public void listener02_2(String message) {
    //     logger.info("2 receive message:{}", message);
    // }


    /**
     * @Description: 测试群组下多个消费者消费(消费者>分区数)
     *
     * 2022-06-24 14:27:39.480  INFO 7428 --- [ntainer#1-3-C-1] o.s.k.l.KafkaMessageListenerContainer    : group03: partitions assigned: []
     *
     * 2022-06-24 14:27:39.491  INFO 7428 --- [ntainer#1-2-C-1] o.s.k.l.KafkaMessageListenerContainer    : group03: partitions assigned: []
     *
     * 2022-06-24 14:27:39.635  INFO 7428 --- [ntainer#1-1-C-1] o.s.k.l.KafkaMessageListenerContainer    : group03: partitions assigned: [topic.demo.three-1
     *
     * 2022-06-24 14:27:39.753  INFO 7428 --- [ntainer#1-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : group03: partitions assigned: [topic.demo.three-0]
     *
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 1 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 0 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 3 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 2 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 5 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 4 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 7 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 9 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.803  INFO 7428 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 6 test message from kafka in topic topic.demo.three
     * 2022-06-24 14:27:39.804  INFO 7428 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group03 receive message:This is 8 test message from kafka in topic topic.demo.three
     *
     *topic的每一个partition只能被同一个group的一个线程消费  这个是kafka很重要的特点 设置concurrency时，注意同一个group的concurrency之和不能大于partitions
     * 否则多余的消费者无法消费消息
     * 避免多线程锁消费同一个消息锁竞争，提高吞吐量
     * 注意 消息消费局部有序（同一个分区） 全局不是有序的
     *
     * @Author Jane
     * @Date: 2022/6/24 11:39
     * @Version 1.0
     */
    @KafkaListener(groupId = "group03", topics = "topic.demo.three", concurrency = "4")
    public void listener03(String message) {
        logger.info("group03 receive message:{}", message);
    }

    /**
     * @Description: 测试不同组 消费同一个topic 互不影响
     *
     * 2022-06-24 14:53:52.524  INFO 5176 --- [ntainer#1-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : group04_1: partitions assigned: [topic.demo.four-0]
     * 2022-06-24 14:53:52.524  INFO 5176 --- [ntainer#1-1-C-1] o.s.k.l.KafkaMessageListenerContainer    : group04_1: partitions assigned: [topic.demo.four-1]

     * 2022-06-24 14:53:52.524  INFO 5176 --- [ntainer#3-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : group04_2: partitions assigned: [topic.demo.four-0]
     * 2022-06-24 14:53:52.524  INFO 5176 --- [ntainer#3-1-C-1] o.s.k.l.KafkaMessageListenerContainer    : group04_2: partitions assigned: [topic.demo.four-1]
     *
     * 2022-06-24 14:53:52.643  INFO 5176 --- [ntainer#3-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 0 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.643  INFO 5176 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 0 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.643  INFO 5176 --- [ntainer#3-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 1 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.643  INFO 5176 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 1 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.643  INFO 5176 --- [ntainer#3-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 3 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 3 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 2 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#3-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 5 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#3-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 2 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 5 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 4 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#3-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 7 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 7 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#3-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 4 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 6 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#3-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 9 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#1-0-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 9 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#3-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 6 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#1-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_1 receive message:This is 8 test message from kafka in topic topic.demo.four
     * 2022-06-24 14:53:52.652  INFO 5176 --- [ntainer#3-1-C-1] c.h.kafka.component.DemoTopicListener    : group04_2 receive message:This is 8 test message from kafka in topic topic.demo.four
     *
     * @Author Jane
     * @Date: 2022/6/24 14:51
     * @Version 1.0
     */
    @KafkaListener(groupId = "group04_1", topics = "topic.demo.four", concurrency = "2")
    public void listener04_1(String message) {
        logger.info("group04_1 receive message:{}", message);
    }

    @KafkaListener(groupId = "group04_2", topics = "topic.demo.four", concurrency = "2")
    public void listener04_2(String message) {
        logger.info("group04_2 receive message:{}", message);
    }



    /**
     * @Description: 测试新消费组订阅topic  spring.kafka.consumer.auto-offset-reset=earliest时，新消费组从头开始消费
     * 不同消费组消费同一个topic  相互隔离 offset互不影响
     * 用于测试test04_3()
     * @Author Jane
     * @Date: 2022/6/24 15:02
     * @Version 1.0
     */
    @KafkaListener(groupId = "group04_3", topics = "topic.demo.four", concurrency = "2")
    public void listener04_3(String message) {
        logger.info("group04_3 receive message:{}", message);
    }


    /**
     * @Description: 测试使用ConsumerRecode
     * @Author Jane
     * @Date: 2022/6/24 15:26
     * @Version 1.0
     */
    @KafkaListener(groupId = "group05", topics = "topic.demo.five")
    public void listener05(ConsumerRecord record) {
        logger.info("group05 receive record:{}", record.toString());
        logger.info("recode message:{}", record.value());
    }


}
