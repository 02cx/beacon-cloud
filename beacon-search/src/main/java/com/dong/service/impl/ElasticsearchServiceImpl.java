package com.dong.service.impl;

import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.SearchException;
import com.dong.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/8 下午5:16
 */
@Slf4j
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    /**
     * 添加成功的result
     */
    private final String CREATED = "created";


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void index(String index, String id, String jsonMsg) throws IOException {
        // 构建插入数据的request
        IndexRequest request = new IndexRequest();
        request.index(index);
        request.id(id);
        request.source(jsonMsg, XContentType.JSON);
        // 将request信息发送到 ES
        IndexResponse result = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        // 校验结果
        if(!CREATED.equalsIgnoreCase(result.getResult().getLowercase())){
            // 校验不通过
            log.info("【ElasticsearchServiceImpl】插入索引数据失败 index={},id={},msg={}", index, id, jsonMsg);
            throw new SearchException(ExceptionEnums.SEARCH_INDEX_ERROR);
        }

        log.info("【ElasticsearchServiceImpl】插入索引数据成功 index={},id={},msg={}", index, id, jsonMsg);
    }
}
