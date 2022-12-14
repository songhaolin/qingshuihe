package com.qingshuihe.common.domain.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.log.LogService;
import com.qingshuihe.common.domain.log.entity.LogEntity;
import com.qingshuihe.common.domain.log.mapper.LogMapper;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/21
 **/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements LogService {
}
