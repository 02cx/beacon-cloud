package com.dong.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/22 上午9:26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileData implements Serializable {
    private String province;
    private String city;
    private String sp;
}
