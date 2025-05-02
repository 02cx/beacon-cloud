package com.dong.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 类描述：在接口模块-策略模块-短信网关模块需要做校验和封装的POJO类对象
 *
 * @author wuyadong
 * @date 2025/4/13 下午12:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardSubmit implements Serializable {

    /**
     * 针对当前短信的唯一标识
     */
    private Long sequenceId;

    /**
     * 客户端ID
     */
    private Long clientId;

    /**
     * 客户端的ip白名单
     */
    private String ip;

    /**
     * 客户端真实ip
     */
    private String realIp;

    /**
     * 客户端apiKey
     */
    private String apiKey;

    /**
     * 客户业务内的uid
     */
    private String uid;

    /**
     * 目标手机号
     */
    private String mobile;

    /**
     * 短信内容的签名
     */
    private String sign;

    /**
     * 签名ID
     */
    private Long signId;

    /**
     * 短信内容
     */
    private String text;

    /**
     * 短信的发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 当前短信的费用
     */
    private Long fee;

    /**
     * 目标手机号的运营商
     */
    private Integer operatorId;


    /**
     * 目标手机号的归属地区号  0451  0455
     */
    private Integer areaCode;

    /**
     * 目标手机号的归属地  哈尔滨，  绥化~
     */
    private String area;

    /**
     * 通道下发的源号码  106934985673485645
     */
    private String srcNumber;

    /**
     * 通道的id信息
     */
    private Long channelId;

    /**
     * 短信的发送类型， 0-验证码短信，1-通知类短信，2-营销类短信
     */
    private int state;

    /**
     * 短信的发送状态， 0-等待ing，1-成功，2-失败
     */
    private int reportState;

    /**
     * 短信的回执信息
     */
    private String reportErrorMsg;

    /***
     * 是否携号转网
     */
    private Boolean isTransfer;


    // 后续再做封装~~~~

}
