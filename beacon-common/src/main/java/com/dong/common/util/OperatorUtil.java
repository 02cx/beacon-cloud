package com.dong.common.util;

import com.dong.common.enums.MobileOperatorEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：根据运营商名称获取运营商id
 *
 * @author wuyadong
 * @date 2025/4/22 上午8:20
 */
public class OperatorUtil {

    private static Map<String,Integer> operatorMap = new HashMap<>();

    static{
        MobileOperatorEnum[] values = MobileOperatorEnum.values();
        for (MobileOperatorEnum value : values) {
            operatorMap.put(value.getOperatorName(),value.getOperatorId());
        }
    }

    /**
     * 根据运营商名称获取运营商id
     * @param operatorName
     * @return
     */
    public static Integer getOperatorId(String operatorName){
        return operatorMap.get(operatorName);
    }
}
