package com.hydsoft.kafka.batch.component;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @title: BatchTopicListener
 * @Description:
 * @Author Jane
 * @Date: 2022/6/27 9:56
 * @Version 1.0
 */
@Component
public class BatchTopicListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Description: 测试批量消费
     * List的size 就是spring.kafka.consumer.max-poll-records配置的值
     * 注意：
     * 需要设置spring.kafka.listener.type=batch
     * @Author Jane
     * @Date: 2022/6/27 10:22
     * @Version 1.0
     */
    @KafkaListener(id = "batch", groupId = "batch", topics = "topic.demo.batch", concurrency = "2")
    public void listener(List<ConsumerRecord> consumerRecordList) {
        logger.info("receive message size:{}", consumerRecordList.size());
        for (ConsumerRecord consumerRecord:consumerRecordList) {
            logger.info("receive consumerRecode:{}", consumerRecord.toString());
        }
    }
}
