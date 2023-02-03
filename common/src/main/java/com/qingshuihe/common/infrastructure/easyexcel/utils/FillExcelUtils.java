package com.qingshuihe.common.infrastructure.easyexcel.utils;

import com.alibaba.excel.EasyExcel;
import com.qingshuihe.common.infrastructure.easyexcel.dto.FillExcelDTO;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/2/3
 **/
public class FillExcelUtils<T> {


    public static <T> void fillExcel(FillExcelDTO fillExcelDTO) {
        EasyExcel.write(fillExcelDTO.getExportPath()).withTemplate(fillExcelDTO.getTemplatePath()).sheet().doFill(fillExcelDTO.getFillObjectList());
    }
}
