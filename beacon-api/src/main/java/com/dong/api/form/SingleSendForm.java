package com.dong.api.form;

import lombok.Data;

/**
 * 类描述：单条短信发送表单数据
 *
 * @author wuyadong
 * @date 2025/4/13 上午12:49
 */
@Data
public class SingleSendForm {
    /** 客户的apikey */
    private String apikey;

    /** 手机号 */
    private String mobile;

    /** 短信内容 */
    private String text;

    /** 客户业务内的uid */
    private String uid;

    /** 0-验证码短信 1-通知类短信 2-营销类短信 */
    private int state;
}
