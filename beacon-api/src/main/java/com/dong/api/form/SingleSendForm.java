package com.dong.api.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * 类描述：单条短信发送表单数据
 *
 * @author wuyadong
 * @date 2025/4/13 上午12:49
 */
@Data
public class SingleSendForm {
    /** 客户的apikey */
    @NotBlank(message = "apikey不能为空")
    private String apikey;

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /** 短信内容 */
    @NotBlank(message = "短信内容不能为空")
    private String text;

    /** 客户业务内的uid */
    private String uid;

    /** 0-验证码短信 1-通知类短信 2-营销类短信 */
    @Range(min = 0, max = 2, message = "state取值范围 0-验证码短信 1-通知类短信 2-营销类短信")
    private int state;
}
