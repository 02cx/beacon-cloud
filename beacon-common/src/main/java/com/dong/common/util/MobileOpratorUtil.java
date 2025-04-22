package com.dong.common.util;

import com.dong.common.domain.MobileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 类描述：调用第三方接口查询手机信息
 *
 * @author wuyadong
 * @date 2025/4/22 上午8:55
 */
public class MobileOpratorUtil {

    private static final String REQ_360_URL = "https://cx.shouji.360.cn/phonearea.php?number=";


    public static MobileResponse getMobileInfo(String number)  {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(REQ_360_URL + number);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String json = EntityUtils.toString(response.getEntity());

                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, MobileResponse.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
