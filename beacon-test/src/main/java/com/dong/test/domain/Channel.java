package com.dong.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 通道表
 * @TableName channel
 */
@TableName(value ="channel")
@Data
public class Channel {
    /**
     * 主键
     */
    @TableId
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

    /**
     * 是否启动 0-已停用 1-启用中
     */
    @JsonIgnore
    private Integer isAvailable;

    /**
     * 创建时间，默认系统时间
     */
    @JsonIgnore
    private Date created;

    /**
     * 创建人id
     */
    @JsonIgnore
    private Long createId;

    /**
     * 修改时间，默认系统时间
     */
    @JsonIgnore
    private Date updated;

    /**
     * 修改人id
     */
    @JsonIgnore
    private Long updateId;

    /**
     * 是否删除 0-未删除 ， 1-已删除
     */
    @JsonIgnore
    private Integer isDelete;

    /**
     * 备用字段1
     */
    @JsonIgnore
    private String extend1;

    /**
     * 备用字段2
     */
    @JsonIgnore
    private String extend2;

    /**
     * 备用字段3
     */
    @JsonIgnore
    private String extend3;

    /**
     * 备用字段4
     */
    @JsonIgnore
    private String extend4;
}