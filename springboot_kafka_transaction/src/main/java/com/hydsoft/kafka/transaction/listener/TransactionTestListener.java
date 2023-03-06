package com.hydsoft.kafka.transaction.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @title: TransactionTestListener
 * @Description:
 * @Author Jane
 * @Date: 2022/6/24 15:47
 * @Version 1.0
 */
@Component
public class TransactionTestListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(groupId = "transaction01", topics = "topic.demo.transaction01")
    public void listener01(ConsumerRecord consumerRecord) {
        logger.info("transaction01 receive record:{}", consumerRecord.toString());
    }
}
