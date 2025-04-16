package com.dong.api.util;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/16 下午9:58
 */
public class MsgTemplateConvert {


    /**
     *   "您的验证码是#code#。如非本人操作，请忽略本短信"
     *   ----->
     *   "^您的验证码是.*?。如非本人操作，请忽略本短信$"
     * @param dbTemplate
     * @return
     */
    public static String convertTemplateToRegex(String dbTemplate) {
        // 仅替换 #code# 为 .*?，其他内容原样匹配
        String regex = dbTemplate.replace("#code#", ".*?");

        // 转义正则特殊字符（排除 # 和已经处理的内容）
        regex = regex.replaceAll("([+^$()\\[\\]{}|\\\\])", "\\\\$1");
        return "^" + regex + "$";
    }
}
