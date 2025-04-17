package com.dong.api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述：构建队列&交换机信息
 *
 * @author wuyadong
 * @date 2025/4/17 下午6:56
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 接口模块发送消息到策略模块的队列
     * @return
     */
    @Bean
    public Queue preSendQueue(){
        return QueueBuilder.durable( "sms_pre_send_topic").build();
    }

}
