package com.dong.test.mapper;

import com.dong.test.domain.MobileArea;
import com.dong.test.domain.MobileBlack;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 20117
* @description 针对表【mobile_black(手机号黑名单表)】的数据库操作Mapper
* @createDate 2025-05-02 22:31:10
* @Entity com.dong.test.domain.MobileBlack
*/
public interface MobileBlackMapper extends BaseMapper<MobileBlack> {

    List<MobileBlack> findAll();

}




