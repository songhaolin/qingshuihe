package com.qingshuihe.common.domain.service.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingshuihe.common.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/21
 **/
@Data
@TableName("sys_log_t")
public class LogEntity extends BaseEntity implements Serializable {

    /**
     * @Description: 日志参数
     * @Date: 2022/9/21
     **/
    private String param;
    /**
     * @Description: 操作
     * @Date: 2022/9/21
     **/
    private String operation;
    /**
     * @Description: 描述
     * @Date: 2022/9/21
     **/
    private String description;
}
