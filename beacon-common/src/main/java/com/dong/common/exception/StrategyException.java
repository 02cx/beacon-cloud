package com.dong.common.exception;

import com.dong.common.enums.ExceptionEnums;

/**
 * 类描述：策略模块异常
 *
 * @author wuyadong
 * @date 2025/4/22 上午8:42
 */
public class StrategyException extends RuntimeException{
    public int code;

    public StrategyException(String message, int code) {
        super(message);
        this.code = code;
    }

    public StrategyException(ExceptionEnums exceptionEnums){
        super(exceptionEnums.getMsg());
        this.code = exceptionEnums.getCode();
    }
}
