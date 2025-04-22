package com.dong.common.enums;

import lombok.Getter;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/22 上午8:17
 */
@Getter
public enum MobileOperatorEnum {

    CHINA_MOBILE(1,"移动"),
    CHINA_UNICOM(2,"联通"),
    CHINA_TELECOM(3,"电信"),
    ;

    private Integer operatorId;

    private String operatorName;


    private MobileOperatorEnum(Integer operatorId, String operatorName) {
        this.operatorId = operatorId;
        this.operatorName = operatorName;
    }
}
