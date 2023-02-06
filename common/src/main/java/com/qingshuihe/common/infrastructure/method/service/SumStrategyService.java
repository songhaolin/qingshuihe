package com.qingshuihe.common.infrastructure.method.service;

/**
 * @Description: 策略模式使用示例
 * @Date: 2023/2/6
 * @Param null: 
 **/
public interface SumStrategyService {

    boolean isCurrentSumStrategy(int temp);
    int sum(int a ,int b);
}
