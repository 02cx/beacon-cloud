package com.dong.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 敏感词
 * @TableName mobile_dirtyword
 */
@TableName(value ="mobile_dirtyword")
@Data
public class MobileDirtyword {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 敏感词
     */
    private String dirtyword;

    /**
     * 创建时间，默认系统时间
     */
    private Date created;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 修改时间，默认系统时间
     */
    private Date updated;

    /**
     * 修改人id
     */
    private Long updateId;

    /**
     * 是否删除 0-未删除 ， 1-已删除
     */
    private Integer isDelete;

    /**
     * 备用字段1
     */
    private String extend1;

    /**
     * 备用字段2
     */
    private String extend2;

    /**
     * 备用字段3
     */
    private String extend3;

    /**
     * 备用字段4
     */
    private String extend4;
}