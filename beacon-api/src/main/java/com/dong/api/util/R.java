package com.dong.api.util;

import com.dong.api.vo.ResultVO;
import com.dong.common.enums.SmsCodeEnum;

import java.util.List;

/**
 * 类描述：响应信息工具类
 *
 * @author wuyadong
 * @date 2025/4/13 上午12:55
 */
public class R {

    public static ResultVO ok() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("success");
        return resultVO;
    }


    public static ResultVO failure(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }


    public static ResultVO failure(SmsCodeEnum smsCodeEnum, List<Object> list) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(smsCodeEnum.getCode());
        resultVO.setMsg(smsCodeEnum.getMsg());
        resultVO.setData(list);
        return resultVO;
    }
}
