package com.dong.cache.domain;

import lombok.Data;

import java.util.Date;

/**
 * 通道表
 *
 * @TableName channel
 */
@Data
public class Channel {
    /**
     * 主键
     */
    private Long id;

    /**
     * 通道名称 如：北京移动、上海联通、深圳电信
     */
    private String channelName;

    /**
     * 通道类型：0-三网 1-移动 2-联通 3-电信
     */
    private Integer channelType;

    /**
     * 通道地区 如：北京 北京、湖北 荆门
     */
    private String channelArea;

    /**
     * 地区号段
     */
    private String channelAreaCode;

    /**
     * 通道短信成本价格（厘/条）
     */
    private Long channelPrice;

    /**
     * 通道协议类型 1-cmpp、2-sgip、3-smgp
     */
    private Integer channelProtocal;

    /**
     * 通道IP地址
     */
    private String channelIp;

    /**
     * 通道端口号
     */
    private Integer channelPort;

    /**
     * 通道账号
     */
    private String channelUsername;

    /**
     * 通道密码
     */
    private String channelPassword;

    /**
     * 账户接入号，如1069777、10684376
     */
    private String channelNumber;

}