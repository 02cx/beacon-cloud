package com.dong.common.constant;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/19 下午5:25
 */
public class RabbitMQConstant {

    public static final String SMS_PRE_SEND = "sms_pre_send_topic";

    public static final String MOBILE_AREA_OPERATOR = "mobile_area_operator_topic";

    /**
     * 写日志到Elasticsearch的队列
     */
    public static final String SMS_WRITE_LOG = "sms_write_log_topic";

    /**
     * 推送客户的报告信息
     */
    public static final String SMS_PUSH_REPORT = "sms_push_report_topic";

    public static final String SMS_GATEWAY = "sms_gateway_topic_";
}
