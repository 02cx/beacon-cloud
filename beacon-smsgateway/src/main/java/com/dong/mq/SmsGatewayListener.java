package com.dong.mq;

import com.dong.common.model.StandardSubmit;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/11 下午8:11
 */
@Slf4j
@Component
public class SmsGatewayListener {

    @RabbitListener(queues = "${gateway.sendtopic}")
    public void consume(String submit, Channel channel, Message mes) throws IOException {
        log.info("【网关模块】短信接收到的MQ消息，短信 = {}", submit);
        // 完成与运营商的交互，发送一次请求，接收两次响应


        // 手动确认消息
        channel.basicAck(mes.getMessageProperties().getDeliveryTag(), false);
    }
}
