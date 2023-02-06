package com.qingshuihe.common.infrastructure.method.service.impl;

import com.qingshuihe.common.infrastructure.method.service.SumStrategyService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/2/6
 **/
@Service
public class BsumStrategyServiceImpl implements SumStrategyService {

    int temp = 2;

    @Override
    public boolean isCurrentSumStrategy(int temp) {
        return Objects.equals(temp,2);
    }
    @Override
    public int sum(int a, int b) {
        return temp+a+b;
    }
}
