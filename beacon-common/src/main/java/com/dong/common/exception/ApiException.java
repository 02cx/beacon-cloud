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
public class ApiException extends RuntimeException{

    public int code;

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ApiException(ExceptionEnums exceptionEnums){
        super(exceptionEnums.getMsg());
        this.code = exceptionEnums.getCode();
    }
}
