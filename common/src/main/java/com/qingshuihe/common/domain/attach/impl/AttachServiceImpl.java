package com.qingshuihe.common.domain.attach.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.attach.AttachService;
import com.qingshuihe.common.domain.attach.mapper.AttachMapper;
import com.qingshuihe.common.domain.attach.entity.AttachEntity;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/15
 **/
@Service
public class AttachServiceImpl extends ServiceImpl<AttachMapper, AttachEntity> implements AttachService {
}
