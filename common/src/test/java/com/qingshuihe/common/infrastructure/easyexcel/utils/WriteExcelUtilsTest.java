package com.qingshuihe.common.infrastructure.easyexcel.utils;

import com.qingshuihe.common.infrastructure.easyexcel.dto.DemoDTO;
import com.qingshuihe.common.infrastructure.easyexcel.dto.WriteExcelDTO;
import org.junit.jupiter.api.Test;

import javax.xml.bind.ValidationException;
import java.util.*;

class WriteExcelUtilsTest {

    List<DemoDTO> datalist = new ArrayList<>();
    WriteExcelDTO customers;

    {
        Random random = new Random();
        String[] grades = {"A", "B", "C", "D", "E"};
        String[] gender = {"男", "女"};
        for (int i = 0; i < 5; i++) {
            DemoDTO demoDTO = new DemoDTO(i, "student" + i, gender[random.nextInt(2)],
                    random.nextInt(100) + 1 + "", grades[random.nextInt(5)],
                    random.nextInt(10000)+random.nextDouble(),new Date() );
            datalist.add(demoDTO);
        }
        String excelPath = "C:\\Users\\song\\Desktop\\excel.xlsx";
        try {
            customers = new WriteExcelDTO(excelPath, datalist);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void exportExcel() {
        WriteExcelUtils.exportExcel(customers);
    }


    @Test
    void exportExcelExcludeColumns() {
        //设置不需导出的字段
        Set<String> strings = new HashSet<>();
        strings.add("name");
        customers.setIncludeColumns(strings);
        WriteExcelUtils.exportExcelExcludeColumns(customers);
    }

    @Test
    void exportExcelIncludeColumns() {
        //增加需导出的字段
        Set<String> strings = new HashSet<>();
        strings.add("name");
        customers.setIncludeColumns(strings);
        WriteExcelUtils.exportExcelIncludeColumns(customers);
    }
    @Test
    void exportExcelToOneSheet() {
        WriteExcelUtils.exportExcelToOneSheet(customers);
    }


    @Test
    void exportExcelToDifferentSheets() {
        customers.setSheets(new String[]{"1", "2"});
        WriteExcelUtils.exportExcelToDifferentSheets(customers);
    }
}