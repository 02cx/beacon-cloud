package com.dong.mq;

import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.model.StandardReport;
import com.dong.config.RabbitMQConfig;
import com.dong.config.RestTemplateConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/11 上午11:26
 */
@Slf4j
@Component
public class PushReportListener {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String SUCCESS = "success";

    // 重试时间间隔
    private int[] delayTime = {0, 15000,30000,60000,300000};


    /**
     *  监控策略模块推送过来的消息（暂时是策略）
     * @param report
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQConstant.SMS_PUSH_REPORT)
    public void consume(StandardReport report, Channel channel, Message message) throws IOException {
        log.info("【推送模块】接收到的MQ消息，短信 = {}", report.toString());
        String callbackUrl = report.getCallbackUrl();
        // 回调地址为空
        if (Strings.isEmpty(callbackUrl)) {
            log.info("【推送模块】客户方没有设置回调地址信息，callbackUrl={}", callbackUrl);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        // 回调地址不为空
        boolean reportFlag = pushReport(report);
        // 如果发送失败，重试
        isResend(report, reportFlag);

        // 手动ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    /**
     * 监听延迟交换机路由过来的消息
     * @param report
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMQConfig.DELAYED_QUEUE)
    public void delayedConsume(StandardReport report, Channel channel,Message message) throws IOException {
        // 1、发送状态报告
        boolean flag = pushReport(report);

        // 2、判断状态报告发送情况
        isResend(report, flag);

        // 手动ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    /**
     * 发送请求，给callbackUrl
     * @param report
     * @return
     */
    private boolean pushReport(StandardReport report) throws JsonProcessingException {
        // 声明返回结果，你默认为false
        boolean flag = false;

        //1、声明发送的参数
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(report);

        //2、声明RestTemplate的模板代码
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            log.info("【推送模块-推送状态报告】 第{}次推送状态报告开始！report = {}",report.getResendCount() + 1,report);
            String result = restTemplate.postForObject("http://" + report.getCallbackUrl(), new HttpEntity<>(body, httpHeaders), String.class);
            flag = SUCCESS.equals(result);
        } catch (RestClientException e) {
        }
        //3、得到响应后，确认是否为SUCCESS
        return flag;
    }

    /**
     * 判断状态报告是否推送成功，失败的话需要发送重试消息
     * @param report
     * @param flag
     */
    private void isResend(StandardReport report, boolean flag) {
        if(!flag){
            // 防止下标越界
            if(report.getResendCount() + 1 == 6){
                return;
            }
            log.info("【推送模块-推送状态报告】 第{}次推送状态报告失败！report = {}",report.getResendCount() + 1,report);
            report.setResendCount(report.getResendCount() + 1);
            rabbitTemplate.convertAndSend(RabbitMQConfig.DELAYED_EXCHANGE, "", report, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    // 设置延迟时间
                    message.getMessageProperties().setDelay(delayTime[report.getResendCount()]);
                    return message;
                }
            });
        }else{
            log.info("【推送模块-推送状态报告】 推送状态报告成功！report = {}",report);
        }
    }
}
