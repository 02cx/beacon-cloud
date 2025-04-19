package com.dong.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 手机号区域表
 * @TableName mobile_area
 */
@TableName(value ="mobile_area")
@Data
public class MobileArea {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 手机号前7位
     */
    private String mobileNumber;

    /**
     * 手机号区域
     */
    private String mobileArea;

    /**
     * 手机号运营商
     */
    private String mobileType;

    /**
     * 地区号段
     */
    private String areaCode;

    /**
     * 地区邮箱
     */
    private String postCode;

    /**
     * 省号段
     */
    private String provinceCode;

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