package com.qingshuihe.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
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

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    Long id;

    String createBy;

    Date createDate;

    String updateBy;

    Date updateDate;

    Integer enable;

    String tenant;
}
