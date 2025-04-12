package com.dong.api.util;

import com.dong.api.vo.ResultVO;

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
}
