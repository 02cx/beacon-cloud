package com.dong.service;

import java.io.IOException;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/8 下午5:14
 */
public interface ElasticsearchService {

    /**
     * 添加索引信息
     * @param index 索引
     * @param id 索引id
     * @param jsonMsg json类型数据
     */
    public void index(String index,String id,String jsonMsg) throws IOException;
}
