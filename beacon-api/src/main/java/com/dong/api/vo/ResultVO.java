package com.dong.api.vo;

import lombok.Data;

/**
 * 类描述：响应结果
 *
 * @author wuyadong
 * @date 2025/4/13 上午12:50
 */
@Data
public class ResultVO {
    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应详细信息
     */
    private Object data;

    /**
     * 短信的计费条数（70个字一条，超出70个字，按照67个字一条发送）
     */
    private Integer count;

    /**
     * 扣费的金额，单位：厘 ，RMB
     */
    private Long fee;

    /**
     * 客户请求携带的uid信息
     */
    private String uid;

    /**
     * 平台内的短信id，64位整型
     */
    private String sid;
}
