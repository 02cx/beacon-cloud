package com.dong.common.domain;

import lombok.Data;

/**
 * 手机号区域表
 * @TableName mobile_area
 */
@Data
public class MobileAreaDTO {
    /**
     * 主键
     */
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


}