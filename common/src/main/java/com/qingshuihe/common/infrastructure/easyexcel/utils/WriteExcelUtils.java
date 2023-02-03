package com.qingshuihe.common.infrastructure.easyexcel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.qingshuihe.common.infrastructure.easyexcel.dto.WriteExcelDTO;

/**
 * @Description:easy
 * @Author: shl
 * @Date: 2023/1/31
 **/
public class WriteExcelUtils<T> {

    /**
     * @Description: 生成excel，方式一：通过excelDo对象简单生成excel
     * @Date: 2023/2/1
     * @Param excelDo:
     **/
    public static <T> void exportExcel(WriteExcelDTO writeExcelDo) {
        EasyExcel.write(writeExcelDo.getExportPath(), writeExcelDo.getDataList().get(0).getClass()).sheet(writeExcelDo.getSheet()).doWrite(writeExcelDo.getDataList());
    }

    /**
     * @Description: 屏蔽导出数据类中的某些字段，导出其他字段
     * 操作方法：1.在对应的类中，将无需导出的字段加上@ExcelIngore
     * 2.在ExcelDo中增加set对象用以存储无需导出的字段的filedName，调用excludeColumnFiledNames()则会屏蔽该集合的字段
     * @Date: 2023/2/1
     * @Param excelDo:
     **/
    public static <T> void exportExcelExcludeColumns(WriteExcelDTO writeExcelDo) {
        EasyExcel.write(writeExcelDo.getExportPath(), writeExcelDo.getDataList().get(0).getClass())
                .excludeColumnFiledNames(writeExcelDo.getExcludeColumns()).sheet(writeExcelDo.getSheet()).doWrite(writeExcelDo.getDataList());
    }
    /**
     * @Description: 只导出数据类中的某些字段，不导出其他字段
     * 操作方法：在ExcelDo中增加set对象用以存储需导出的字段的filedName，调用includeColumnFiledNames()则会只导出该集合的字段
     * @Date: 2023/2/1
     * @Param excelDo:
     **/
    public static <T> void exportExcelIncludeColumns(WriteExcelDTO writeExcelDo) {
        EasyExcel.write(writeExcelDo.getExportPath(), writeExcelDo.getDataList().get(0).getClass())
                .includeColumnFiledNames(writeExcelDo.getIncludeColumns()).sheet(writeExcelDo.getSheet()).doWrite(writeExcelDo.getDataList());
    }
    /**
     * @Description: 生成excel，方式二：单独生成excelWriter和sheetWriter，组合后生成excel
     * 将数据写入同一个sheet页
     * @Date: 2023/2/1
     * @Param excelDo:
     **/
    public static <T> void exportExcelToOneSheet(WriteExcelDTO writeExcelDo) {
        ExcelWriter excelWriter = EasyExcel.write(writeExcelDo.getExportPath(), writeExcelDo.getDataList().get(0).getClass()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(writeExcelDo.getSheet()).build();
        excelWriter.write(writeExcelDo.getDataList(), writeSheet);
        excelWriter.finish();
    }
    /**
     * @Description: 将数据写入不同的sheet页
     * @Date: 2023/2/2
     * @Param excelDo:
     **/
    public static <T> void exportExcelToDifferentSheets(WriteExcelDTO writeExcelDo) {
        ExcelWriter excelWriter = EasyExcel.write(writeExcelDo.getExportPath(), writeExcelDo.getDataList().get(0).getClass()).build();
        for (int i = 0; i < writeExcelDo.getSheets().length; i++) {
            WriteSheet writeSheet = EasyExcel.writerSheet(writeExcelDo.getSheets()[i]).build();
            excelWriter.write(writeExcelDo.getDataList(), writeSheet);
        }
        excelWriter.finish();
    }

}
