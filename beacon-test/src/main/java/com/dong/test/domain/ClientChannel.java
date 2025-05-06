package com.dong.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 客户通道表
 * @TableName client_channel
 */
@TableName(value ="client_channel")
@Data
public class ClientChannel {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 客户id，对应client_business表
     */
    private Long clientId;

    /**
     * 通道id，对应channel表
     */
    private Long channelId;

    /**
     * 通道权重
     */
    private Integer clientChannelWeight;

    /**
     * 客户通道短信价格（厘/条）
     */
    private Long clientChannelPrice;

    /**
     * 下发扩展号 如：通道接入号为1069886，后面可以扩展数字，最长不超过20位
     */
    private String clientChannelNumber;

    /**
     * 是否启动 0-启用中 1-已停用
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