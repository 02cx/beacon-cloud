package com.dong.common.enums;

import lombok.Getter;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/16 下午4:02
 */
@Getter
public enum ExceptionEnums {
    ERROR_APIKEY(-1,"非法的apikey"),
    IP_NOT_WHITE(-2,"请求的ip不在白名单内"),
    ERROR_SIGN(-3,"无可用签名"),
    ERROR_TEMPLATE(-4,"无可用模板"),
    ERROR_MOBILE(-5,"手机号格式不正确"),
    BALANCE_NOT_ENOUGH(-6,"手客户余额不足"),
    MOBILE_EMPTY(-7,"手机号为空"),
    DIRTWORD_EXIST(-8,"包含敏感词"),
    BLACK_GLOBAL(-9,"当前手机号属于全局黑名单"),
    BLACK_CLIENT(-10,"当前手机号属于客户黑名单"),
    ONE_MINUS_LIMIT(-11,"一分钟内只能发送一次短信"),
    ONE_HOURS_LIMIT(-12,"一个小时内只能发送三次短信"),
    NO_CHANNEL(-13,"没有可能的通道信息"),
    UNKNOWN_ERROR(-20,"未知的错误"),
    SEARCH_INDEX_ERROR(-21,"索引添加失败异常"),
    ;


    private Integer code;

    private String msg;

    ExceptionEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
