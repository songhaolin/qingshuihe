package com.qingshuihe.common.infrastructure.method.controller;

import com.qingshuihe.common.infrastructure.exception.AppexcepitonCodeMsg;
import com.qingshuihe.common.infrastructure.method.service.SumStrategyService;
import com.qingshuihe.common.infrastructure.method.vo.StrategyVO;
import com.qingshuihe.common.infrastructure.response.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/2/6
 **/
@RestController
public class SumStrategyController implements ISumStrategyController {

    //这里可以使用list<接口类>将该接口的实现类均注入进去
    @Autowired
    private List<SumStrategyService> strategyServiceList;

    @Override
    @PostMapping("/study/strategy")
    public Resp studyStrategy(StrategyVO strategyVO) {
        SumStrategyService sumStrategyService = strategyServiceList.stream().filter(s -> s.isCurrentSumStrategy(strategyVO.getTemp())).findFirst().orElse(null);
        if (sumStrategyService == null) {
            return Resp.error(AppexcepitonCodeMsg.INVAILD_PARAMETER.getCode(),AppexcepitonCodeMsg.INVAILD_PARAMETER.getMessage());
        }
        int res = sumStrategyService.sum(strategyVO.getA(), strategyVO.getB());

        return Resp.success(res);
    }
}
