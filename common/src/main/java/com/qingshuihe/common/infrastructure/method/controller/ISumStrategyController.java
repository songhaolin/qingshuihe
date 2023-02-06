package com.qingshuihe.common.infrastructure.method.controller;

import com.qingshuihe.common.infrastructure.method.vo.StrategyVO;
import com.qingshuihe.common.infrastructure.response.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "策略模式测试")
public interface ISumStrategyController {

    @ApiOperation(value = "/study/strategy",notes = "策略模式测试接口")
    Resp studyStrategy(StrategyVO strategyVO);
}