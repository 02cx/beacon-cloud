package com.dong.cache.domain;

import lombok.Data;

import java.util.Date;

/**
 * 短信模板
 *
 * @TableName client_template
 */
@Data
public class ClientTemplate {
    /**
     * 主键
     */
    private Long id;

    /**
     * 签名id，对应client_sign
     */
    private Long signId;

    /**
     * 模板内容
     */
    private String templateText;

    /**
     * 模板类型 0-验证码类，1-通知类，2-营销类
     */
    private Integer templateType;

    /**
     * 审核是否通过 0-审核中 1-审核不通过 2-审核已通过
     */
    private Integer templateState;

    /**
     * 使用场景 0-网站 1-APP 2-微信
     */
    private Integer useId;

    /**
     * 网站地址（防轰炸，验证码截图）
     */
    private String useWeb;

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