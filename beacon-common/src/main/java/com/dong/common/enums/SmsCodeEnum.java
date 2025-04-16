package com.dong.common.enums;

import lombok.Getter;

/**
 * 类描述：响应信息中code和msg的对应
 *
 * @author wuyadong
 * @date 2025/4/13 上午1:00
 */
@Getter
public enum SmsCodeEnum {
    PARAMETER_ERROR(-10, "参数错误！"),;

    private Integer code;

    private String msg;

    SmsCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
