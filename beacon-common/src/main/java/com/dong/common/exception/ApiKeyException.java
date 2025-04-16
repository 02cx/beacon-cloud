package com.dong.common.exception;

import com.dong.common.enums.ExceptionEnums;
import lombok.Getter;

/**
 * 类描述：apiKey异常
 *
 * @author wuyadong
 * @date 2025/4/16 下午4:00
 */
@Getter
public class ApiKeyException extends RuntimeException{

    public int code;

    public ApiKeyException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ApiKeyException(ExceptionEnums exceptionEnums){
        super(exceptionEnums.getMsg());
        this.code = exceptionEnums.getCode();
    }
}
