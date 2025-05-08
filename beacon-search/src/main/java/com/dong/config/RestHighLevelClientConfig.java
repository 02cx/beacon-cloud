package com.dong.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 类描述：配置Elasticsearch连接
 *
 * @author wuyadong
 * @date 2025/5/8 下午5:08
 */
@Configuration
@Slf4j
public class RestHighLevelClientConfig {

    @Value("#{'${elasticsearch.hostAndPorts}'.split(',')}")
    private List<String> hostAndPorts;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        // 初始化连接ES的HttpHost信息
        HttpHost[] httpHosts = new HttpHost[hostAndPorts.size()];
        for (int i = 0; i < hostAndPorts.size(); i++) {
            String[] hostAndPort = hostAndPorts.get(i).split(":");
            httpHosts[i] = new HttpHost(hostAndPort[0],Integer.parseInt(hostAndPort[1]));
        }
        log.info("【初始化ES连接】 = {}", hostAndPorts);

        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);

        // 构建连接ES的client对象
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);

        // 返回
        return restHighLevelClient;
    }
}
