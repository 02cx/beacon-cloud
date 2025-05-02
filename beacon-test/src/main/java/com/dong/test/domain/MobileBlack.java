package com.dong.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 手机号黑名单表
 * @TableName mobile_black
 */
@TableName(value ="mobile_black")
@Data
public class MobileBlack {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 黑名单手机号
     */
    private String blackNumber;

    /**
     * 录入方式 0-手动导入 1-第三方API 2-用户退订
     */
    private Integer blackType;

    /**
     * 黑名单类型 0-全局黑名单  其他-客户黑名单
     */
    private Integer clientId;

    /**
     * 创建时间，默认系统时间
     */
    private Date created;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 修改时间，默认系统时间
     */
    private Date updated;

    /**
     * 修改人id
     */
    private Long updateId;

    /**
     * 是否删除 0-未删除 ， 1-已删除
     */
    private Integer isDelete;

    /**
     * 备用字段1
     */
    private String extend1;

    /**
     * 备用字段2
     */
    private String extend2;

    /**
     * 备用字段3
     */
    private String extend3;

    /**
     * 备用字段4
     */
    private String extend4;
}