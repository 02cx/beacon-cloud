package com.dong.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 携号转网表
 * @TableName mobile_transfer
 */
@TableName(value ="mobile_transfer")
@Data
public class MobileTransfer {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 手机号
     */
    private String transferNumber;

    /**
     * 区域
     */
    private String areaCode;

    /**
     * 最初归属运营商 1-移动 2-联通 3-电信
     */
    private Integer initIsp;

    /**
     * 转网后运营商 1-移动 2-联通 3-电信
     */
    private Integer nowIsp;

    /**
     * 是否转网 0-未转网 1-已转网
     */
    private Integer isTransfer;

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