package com.dong.mq;

import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.model.StandardSubmit;
import com.dong.service.ElasticsearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/8 下午5:11
 */
@Slf4j
@Component
public class SmsWriteLogListener {

    private final String INDEX = "sms_submit_log_";
    @Autowired
    private ElasticsearchService elasticsearchService;

    @RabbitListener(queues = RabbitMQConstant.SMS_WRITE_LOG)
    public void consume(StandardSubmit standardSubmit, Channel channel, Message message) throws IOException {
        log.info("【搜索模块】短信接收到的MQ消息，短信 = {}", standardSubmit.toString());
        // 接收MQ的消息，进一步处理
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMsg = objectMapper.writeValueAsString(standardSubmit);
        elasticsearchService.index(INDEX + LocalDateTime.now().getYear(), standardSubmit.getSequenceId().toString(), jsonMsg);
        // 手动确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


}
