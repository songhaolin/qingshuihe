package com.qingshuihe.common.domain.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingshuihe.common.domain.log.entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/21
 **/
@Mapper
public interface LogMapper extends BaseMapper<LogEntity> {
}
