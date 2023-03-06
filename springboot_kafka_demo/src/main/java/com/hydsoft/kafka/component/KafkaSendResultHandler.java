package com.hydsoft.kafka.component;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @title: KafkaSendResultHandler
 * @Description:
 * @Author Jane
 * @Date: 2022/6/24 15:16
 * @Version 1.0
 */
@Component
public class KafkaSendResultHandler implements ProducerListener {

    private Logger logger = LoggerFactory.getLogger(KafkaSendResultHandler.class);

    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        logger.info("producerRecord :{} send success", producerRecord.toString());
    }

    @Override
    public void onError(ProducerRecord producerRecord, Exception exception) {
        String errorMessage = exception.getMessage();
        logger.info("producerRecord :{} send fail, error:{}", producerRecord.toString(), errorMessage);
    }
}
