package com.qingshuihe.common.infrastructure.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:自定义读取excel文件的监听器，用来将读取到的数据转为list<T>,方便后续使用
 * 该监听器是后续EasyExcel.read()方法的必备参数,自定义需要继承extends AnalysisEventListener
 * 增加@Getter方便后续获取读取到的数据
 * @Author: shl
 * @Date: 2023/2/2
 **/
@Getter
public class ReadExcelListener<T> extends AnalysisEventListener {

    private List<T> dataList = new ArrayList<>();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据："+o);
        dataList.add((T)o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("解析到完整数据："+dataList);
    }
}
