package com.dong.api.controller;

import com.dong.api.filter.CheckFilterContext;
import com.dong.api.form.SingleSendForm;
import com.dong.api.util.R;
import com.dong.api.vo.ResultVO;
import com.dong.common.model.StandardSubmit;
import com.dong.common.util.SnowflakeIdGenerator;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类描述： 映射请求&接收参数&设置响应结果
 *
 * @author wuyadong
 * @date 2025/4/13 上午12:48
 */
@Slf4j
@RestController
@RefreshScope
@RequestMapping("/sms")
public class SmsController {


    @Value("${headers}")
    private String headers;

    @Autowired
    private CheckFilterContext checkFilterContext;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @PostMapping(value = "/single_send", produces = "application/json;charset=utf-8")
    public ResultVO singleSend(@RequestBody @Validated SingleSendForm singleSendForm, HttpServletRequest request) {
        // 获取客户端的真实IP
        String realIP = getRealIP(request);
        // 构建StandardSubmit，各种封装校验
        StandardSubmit ssf = new StandardSubmit();
        ssf.setRealIp(realIP);
        ssf.setApiKey(singleSendForm.getApikey());
        ssf.setMobile(singleSendForm.getMobile());
        ssf.setText(singleSendForm.getText());
        ssf.setState(singleSendForm.getState());
        ssf.setUid(singleSendForm.getUid());
        // 调用策略模式的校验链
        checkFilterContext.check(ssf);

        // 基于雪花算法生成唯一id，并添加到StandardSubmit对象中
        ssf.setSequenceId(SnowflakeIdGenerator.getInstance(1, 1).nextId());
        log.info("【请求参数】 {}", ssf);

        //=========================发送到MQ，交给策略模块处理=========================================
        rabbitTemplate.convertAndSend("sms_pre_send_topic",ssf,new CorrelationData(ssf.getSequenceId().toString()));

        return R.ok();
    }

    /**
     * 获取客户端的真是IP
     * @param request
     * @return
     */
    private String getRealIP(HttpServletRequest request) {

        // 将动态配置中的头部信息进行拆分，并过滤掉空格和空串
        List<String> headerList = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .splitToList(headers);

        // 遍历头部信息，并获取第一个不为空的IP地址
        String realIp = headerList.stream()
                .map(header -> {
                    String ip = request.getHeader(header);
                    if ("x-forwarded-for".equalsIgnoreCase(header) && !Strings.isNullOrEmpty(ip)) {
                        ip = Splitter.on(",")
                                .trimResults()
                                .omitEmptyStrings()
                                .splitToList(ip)
                                .get(0);
                    }
                    return ip;
                })
                .filter(ip -> !Strings.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip))
                .findFirst()
                .orElse(request.getRemoteAddr());
        // 返回获取到的IP地址
        return realIp;
    }


/*    private String getRealIP(HttpServletRequest request) {
        String ip;
        // 1.基于请求头的x-forwarded-for获取ip
        String ips = request.getHeader("x-forwarded-for");
        ip = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(ips).get(0);
        // 2.基于请求头的x-real-ip获取ip
        if(Strings.isNullOrEmpty(ip) || "unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("x-real-ip");
        }
        // 3.Apache的服务器 基于请求头的proxy-client-ip获取ip
        if(Strings.isNullOrEmpty(ip) || "unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("proxy-client-ip");
        }

        // 4.Tomcat的服务器 基于请求头的wl-proxy-client-ip获取ip
        if(Strings.isNullOrEmpty(ip) || "unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("wl-proxy-client-ip");
        }

        // 5.基于其他的代理服务器的方式获取请求头的IP地址
        if(Strings.isNullOrEmpty(ip) || "unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("http_client_ip");
        }

        // 6.上面的方式都没有获取到ip地址，基于最传统的方式获取
        if(Strings.isNullOrEmpty(ip) || "unknow".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }

        return ip;
    }*/


}
