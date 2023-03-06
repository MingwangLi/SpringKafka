package com.hydsoft.kafka.consumer.ack.component;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @title: ConsumerAckTopicListener
 * @Description:
 * @Author Jane
 * @Date: 2022/6/27 11:53
 * @Version 1.0
 */
@Component
public class ConsumerAckTopicListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(id = "consumerAck", groupId = "consumerAck", topics = "topic.consumer.ack")
    public void listener(ConsumerRecord consumerRecord, Acknowledgment acknowledgment) {
        logger.info("receive message:{}", consumerRecord.toString());
        try{
            //throw new RuntimeException("unknown error");
            //Do Business 处理业务
            //处理完业务 手动提交offset
            acknowledgment.acknowledge();
        }catch (Exception e) {
            logger.error("consumer message:{} error:{}", consumerRecord.value(), e.getMessage());
            //业务处理异常 回滚之前操作 不提交offset 只要offset不提交 消息会一直消费直至提交offset
            //rollback;
        }
    }
}
