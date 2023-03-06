package com.hydsoft.kafka.transaction;

import com.hydsoft.kafka.transaction.component.MyKafkaTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title: TransactionTest
 * @Description:
 * @Author Jane
 * @Date: 2022/6/24 15:44
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private MyKafkaTemplate myKafkaTemplate;

    /**
     * @Description: 测试使用executeInTransaction()使用事务
     * 如果发生异常 会走到ProducerListener onError方法  error:Failing batch since transaction was aborted  消息也不会发送出去
     * 注意：
     * 1、需要设置spring.kafka.producer.acks=-1
     * 2、需要配置spring.kafka.producer.transaction-id-prefix=kafka_tx_（是否开启事务是根据是否配置transaction-id-prefix）
     * @Author Jane
     * @Date: 2022/6/24 15:45
     * @Version 1.0
     */
    @Test
    public void test01() {
        kafkaTemplate.executeInTransaction(kafkaOperations -> {
            kafkaOperations.send("topic.demo.transaction01", "This is a test message about transaction in topic.demo.transaction01");
            throw new RuntimeException();
            //return null;
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * @Description: 消息正常发送 消费
     * 并不是@Transactional没生效，而是测试方法不对
     * @Author Jane
     * @Date: 2022/6/24 16:09
     * @Version 1.0
     */
    @Test
    @Transactional
    public void test02() {
        kafkaTemplate.send("topic.demo.transaction01", "This is another test message about transaction in topic.demo.transaction01");
        if (true) {
            throw new RuntimeException();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * @Description: 测试使用@Transactional控制kafka消息发送事务
     * 注意：
     * 不能在@Test方法上加 @Transactional测试 不然不会生效 需要抽取出来
     * @Author Jane
     * @Date: 2022/6/24 16:05
     * @Version 1.0
     */
    @Test
    public void test03() {
        myKafkaTemplate.testTransaction();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }
}
