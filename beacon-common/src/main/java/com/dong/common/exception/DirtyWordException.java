package com.dong.common.exception;

import com.dong.common.enums.ExceptionEnums;
import lombok.Getter;

/**
 * 类描述：敏感词异常
 *
 * @author wuyadong
 * @date 2025/5/1 上午11:50
 */
@Getter
public class DirtyWordException extends RuntimeException{

    public int code;

    public DirtyWordException(String message, int code) {
        super(message);
        this.code = code;
    }

    public DirtyWordException(ExceptionEnums exceptionEnums){
        super(exceptionEnums.getMsg());
        this.code = exceptionEnums.getCode();
    }

}
