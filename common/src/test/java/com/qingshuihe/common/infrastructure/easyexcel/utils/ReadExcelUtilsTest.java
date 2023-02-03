package com.qingshuihe.common.infrastructure.easyexcel.utils;

import com.qingshuihe.common.infrastructure.easyexcel.dto.DemoDTO;
import com.qingshuihe.common.infrastructure.easyexcel.dto.Demo1DTO;
import com.qingshuihe.common.infrastructure.easyexcel.dto.ReadExcelDTO;
import com.qingshuihe.common.infrastructure.easyexcel.dto.SheetsAndClass;
import org.junit.jupiter.api.Test;

import javax.xml.bind.ValidationException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ReadExcelUtilsTest {


    String excelPath = "C:\\Users\\song\\Desktop\\excel.xlsx";

    @Test
    void readExcel() throws ValidationException, FileNotFoundException {


        ReadExcelDTO demo = new ReadExcelDTO(excelPath);
        demo.setClassT(DemoDTO.class);
        ReadExcelUtils.readExcel(demo);
    }

    @Test
    void readExcelAll() throws ValidationException, FileNotFoundException {
        ReadExcelDTO demo = new ReadExcelDTO(excelPath);
        demo.setClassT(DemoDTO.class);
        ReadExcelUtils.readExcelAll(demo);
    }
    @Test
    void readExcelWithDiffentSheets() throws ValidationException, FileNotFoundException {
        ReadExcelDTO demo = new ReadExcelDTO(excelPath);
        ArrayList<SheetsAndClass> sheetsAndClasses = new ArrayList<>();
        {
            SheetsAndClass sheetsAndClass = new SheetsAndClass();
            sheetsAndClass.setSheets("0");
            sheetsAndClass.setClassT(DemoDTO.class);
            sheetsAndClasses.add(sheetsAndClass);
        }
        {
            SheetsAndClass sheetsAndClass = new SheetsAndClass();
            sheetsAndClass.setSheets("1");
            sheetsAndClass.setClassT(Demo1DTO.class);
            sheetsAndClasses.add(sheetsAndClass);
        }
        demo.setSheetsAndClassList(sheetsAndClasses);
        Map<String, List<Object>> listMap = ReadExcelUtils.readExcelWithDiffentSheets(demo);
        //如何使用解析出来的数据
        List<DemoDTO> demoDTOList = new ArrayList<>();
        for (Map.Entry<String,List<Object>> map: listMap.entrySet()
             ) {
            if (map.getValue().get(0) instanceof DemoDTO){
                map.getValue().forEach(demoPOJO->{
                    DemoDTO demoDTO1 = (DemoDTO)demoPOJO;
                    demoDTOList.add(demoDTO1);
                });
            }
        }
        System.out.println(demoDTOList);
    }
}