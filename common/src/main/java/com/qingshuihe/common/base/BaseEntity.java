package com.qingshuihe.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/5
 **/
@Data
public class BaseEntity implements Serializable {

    /**
     * 数据库唯一标识
     */
    @TableId(value="id",type = IdType.ASSIGN_ID)
    Long id;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT )
    String  createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT )
    Date createDate;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE )
    String  updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE )
    Date   updateDate;

    /**
     * 是否有效(0:有效，1：删除)
     */
    Integer   enable;
    /**
     * 租户编码
     */
    @TableField(fill = FieldFill.INSERT )
    String  tenant;
}
