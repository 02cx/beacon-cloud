package com.dong.api.handler;

import com.dong.api.enums.SmsCodeEnum;
import com.dong.api.util.R;
import com.dong.api.vo.ResultVO;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;


/**
 * @author kenx
 * @version 1.0
 * @date 2021/6/17 20:19
 * 全局异常统一处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 json 请求体调用接口对象参数校验失败抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO jsonParamsException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List errorList = Lists.newArrayList();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msg = String.format("%s:%s；", fieldError.getField(), fieldError.getDefaultMessage());
            errorList.add(msg);
        }
        return R.failure(SmsCodeEnum.PARAMETER_ERROR, errorList);
    }


    /**
     * 处理单个参数校验失败抛出的异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVO ParamsException(ConstraintViolationException e) {

        List errorList = Lists.newArrayList();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            StringBuilder message = new StringBuilder();
            Path path = violation.getPropertyPath();
            String[] pathArr = Splitter.on(".").splitToList(path.toString()).toArray(new String[0]);
            String msg = message.append(pathArr[1]).append(violation.getMessage()).toString();
            errorList.add(msg);
        }
        return R.failure(SmsCodeEnum.PARAMETER_ERROR, errorList);
    }


}
