package com.qingshuihe.common.infrastructure.mq;

import com.alibaba.fastjson.JSONObject;
import com.qingshuihe.common.utils.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/21
 **/
@Component
@Slf4j
public class ProductMessageHandler {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendLog(Object data){
        log.info("生产消息:"+ JSONObject.toJSONString(data));
        //第一个参数是topic，第二个是消息
        kafkaTemplate.send(CommonConstant.KAFKA_TOPIC_QINGSHUIHE_LOG,JSONObject.toJSONString(data));
    }
}
