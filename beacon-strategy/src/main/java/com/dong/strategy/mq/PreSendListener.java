package com.dong.strategy.mq;

import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.exception.StrategyException;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.filter.StrategyFilterContext;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private StrategyFilterContext strategyFilterContext;

    @RabbitListener(queues = RabbitMQConstant.SMS_PRE_SEND)
    public void preSend(StandardSubmit standardSubmit, Message message, Channel channel) throws IOException {
        log.info("【策略模块】短信接收到的MQ消息，短信 = {}", standardSubmit.toString());
        try{
            // 处理业务
            strategyFilterContext.strategy(standardSubmit);

            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (StrategyException e){
            log.error("【策略模块】短信处理失败，短信 = {},异常信息 = {}", standardSubmit.toString(), e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
