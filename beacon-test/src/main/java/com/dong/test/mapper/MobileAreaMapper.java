package com.dong.test.mapper;

import com.dong.test.domain.MobileArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.test.domain.MobileAreaDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 20117
* @description 针对表【mobile_area(手机号区域表)】的数据库操作Mapper
* @createDate 2025-04-19 18:33:44
* @Entity com.dong.test.domain.MobileArea
*/
public interface MobileAreaMapper extends BaseMapper<MobileArea> {



    public List<MobileAreaDTO> findAll();

}




