package com.dong.test.mapper;

import com.dong.test.domain.MobileDirtyword;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 20117
* @description 针对表【mobile_dirtyword(敏感词)】的数据库操作Mapper
* @createDate 2025-04-27 11:53:11
* @Entity com.dong.test.domain.MobileDirtyword
*/
public interface MobileDirtywordMapper extends BaseMapper<MobileDirtyword> {


    /**
     * 查询出所有的敏感词
     * @return
     */
    List<String> findDirtyword();
}




