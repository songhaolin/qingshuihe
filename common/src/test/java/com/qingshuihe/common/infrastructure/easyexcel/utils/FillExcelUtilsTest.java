package com.qingshuihe.common.infrastructure.easyexcel.utils;

import com.qingshuihe.common.infrastructure.easyexcel.dto.FillExcelDTO;
import org.junit.jupiter.api.Test;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;

class FillExcelUtilsTest {

    @Test
    void fillExcel() throws ValidationException {
        ArrayList<DemoFillDTO> demoFillDTOS = new ArrayList<>();
        for (int i = 0; i <5; i++) {
            DemoFillDTO demoFillDTO = new DemoFillDTO();
            demoFillDTO.setName("user"+i);
            demoFillDTO.setGender(i+"");
            demoFillDTOS.add(demoFillDTO);
        }
        String templatePath =  "C:\\Users\\song\\Desktop\\template.xlsx";
        String exportPath = "C:\\Users\\song\\Desktop\\excel111.xlsx";
        FillExcelDTO fillExcelDTO = new FillExcelDTO(templatePath, exportPath);
        fillExcelDTO.setFillObjectList(demoFillDTOS);
        FillExcelUtils.fillExcel(fillExcelDTO);

    }
}