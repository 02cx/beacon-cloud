package com.dong.test.mapper;

import com.dong.test.domain.MobileTransfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 20117
* @description 针对表【mobile_transfer(携号转网表)】的数据库操作Mapper
* @createDate 2025-05-02 22:37:11
* @Entity com.dong.test.domain.MobileTransfer
*/
public interface MobileTransferMapper extends BaseMapper<MobileTransfer> {

    List<MobileTransfer> findAll();

}




