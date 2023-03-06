// package com.hydsoft.kafka.transaction.configuraton;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.core.DefaultKafkaProducerFactory;
// import org.springframework.kafka.core.ProducerFactory;
// import org.springframework.kafka.transaction.KafkaTransactionManager;
// import java.util.Map;
//
// /**
//  * @title: KfkaConfiguration
//  * @Description: 使用spring.kafka.producer.transaction-id-prefix=kafka_tx_代替
//  * @Author Jane
//  * @Date: 2022/6/24 16:26
//  * @Version 1.0
//  */
// @Configuration
// public class KafkaConfiguration {
//
//     private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//     @Autowired
//     private KafkaProperties kafkaProperties;
//
//
//     /**
//      * @Description: 配置ProducerFactory的transactionIdPrefix 是否开启事务是根据 transactionIdPrefix != null 来判断的
//      * @Author Jane
//      * @Date: 2022/6/24 16:28
//      * @Version 1.0
//      */
//     @Bean
//     public ProducerFactory initProducerFactory() {
//         // error  org.springframework.transaction.CannotCreateTransactionException: Could not create Kafka transaction; nested exception is org.apache.kafka.common.KafkaException: Failed to construct kafka producer
//         //Map<String, Object> producerProperties = kafkaProperties.getProducer().buildProperties();
//         Map<String, Object> producerProperties = kafkaProperties.buildProducerProperties();
//         DefaultKafkaProducerFactory defaultKafkaProducerFactory = new DefaultKafkaProducerFactory(producerProperties);
//         boolean beforeFlag = defaultKafkaProducerFactory.transactionCapable();
//         logger.info("事务before开关:{}", beforeFlag);
//         defaultKafkaProducerFactory.setTransactionIdPrefix("kafka_tx_");
//         boolean afterFlag = defaultKafkaProducerFactory.transactionCapable();
//         logger.info("事务after开关:{}", afterFlag);
//         return defaultKafkaProducerFactory;
//     }
//
//     /**
//      * @Description: 事务管理器
//      * @Author Jane
//      * @Date: 2022/6/24 16:28
//      * @Version 1.0
//      */
//     @Bean
//     public KafkaTransactionManager initKafkaTransactionManager(ProducerFactory producerFactory) {
//         boolean flag = producerFactory.transactionCapable();
//         logger.info("事务last开关:{}", flag);
//         KafkaTransactionManager kafkaTransactionManager = new KafkaTransactionManager(producerFactory);
//         return kafkaTransactionManager;
//     }
// }
