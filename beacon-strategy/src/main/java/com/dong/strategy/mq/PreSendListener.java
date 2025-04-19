package com.dong.strategy.mq;

import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.model.StandardSubmit;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 类描述：MQ的监听器
 *
 * @author wuyadong
 * @date 2025/4/19 下午5:24
 */
@Component
@Slf4j
public class PreSendListener {

    @RabbitListener(queues = RabbitMQConstant.SMS_PRE_SEND)
    public void preSend(StandardSubmit standardSubmit, Message message, Channel channel) throws IOException {
        log.info("【策略模块】短信接收到的MQ消息，短信 = {}", standardSubmit.toString());
        // 处理业务

        // 手动确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
