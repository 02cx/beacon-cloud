package com.dong.test.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 客户余额表
 *
 * @TableName client_balance
 */
@TableName(value = "client_balance")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientBalance {
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
     * 用户余额（单位：厘）
     */
    private Long balance;

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