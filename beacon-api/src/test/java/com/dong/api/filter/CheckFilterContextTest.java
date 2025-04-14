package com.dong.api.filter;


import com.google.common.base.Splitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/5 下午11:11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckFilterContextTest {

    @Autowired
    private CheckFilterContext checkFilterContext;

    @Test
    public void check() {
        String input = "1233445";
        String result = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(input).get(0);
        System.out.println(result);
    }
}