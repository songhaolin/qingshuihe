package com.qingshuihe.common.infrastructure.mq;

import com.alibaba.fastjson.JSONObject;
import com.qingshuihe.common.domain.log.LogService;
import com.qingshuihe.common.domain.log.entity.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/21
 **/
@Component
@Slf4j
public class ConsumerMessageListener {

    @Autowired
    private LogService logService;

    //使用注解@KafkaListener监听mq中的消息，如果对应的group和topic下出现新的消息则进行消费
//    @KafkaListener(groupId = CommonConstant.KAFKA_GROUP_QINGSHUIHE_LOG,topics = CommonConstant.KAFKA_TOPIC_QINGSHUIHE_LOG)
    public void onConsumerLog(ConsumerRecord<?,?> record){
        log.info("消费消息：topic:"+record.topic()+";value:"+record.value());
        LogEntity logEntity = JSONObject.parseObject(new String(record.value().toString()),LogEntity.class);
        logService.save(logEntity);
    }
}
