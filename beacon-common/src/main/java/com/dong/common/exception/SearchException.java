package com.dong.common.exception;

import com.dong.common.enums.ExceptionEnums;

/**
 * 类描述：搜索模块异常
 *
 * @author wuyadong
 * @date 2025/4/22 上午8:42
 */
public class SearchException extends RuntimeException{
    public int code;

    public SearchException(String message, int code) {
        super(message);
        this.code = code;
    }

    public SearchException(ExceptionEnums exceptionEnums){
        super(exceptionEnums.getMsg());
        this.code = exceptionEnums.getCode();
    }
}
