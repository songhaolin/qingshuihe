package com.qingshuihe.common.infrastructure.easyexcel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.qingshuihe.common.infrastructure.easyexcel.dto.ReadExcelDTO;
import com.qingshuihe.common.infrastructure.easyexcel.dto.SheetsAndClass;
import com.qingshuihe.common.infrastructure.easyexcel.listener.ReadExcelListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/2/2
 **/
public class ReadExcelUtils<T> {
    /**
     * @Description: 简单读取导入的excel文件，默认读取第一页，即.sheet()
     * @Date: 2023/2/2
     * @Param excelDo，其中需要包括重要信息，如输入地址、文件或者流:
     * @Param Class T，泛型类，需要获取的数据的类型
     * 这里也可以使用read.path或者file，为了将读取方法统一，在excelDo中的构造方法处统一转为了输入流，这样可以保持只用一个方法
     * EasyExcel.read(excelDo.getImportPath(),T, listener).sheet().doRead();
     *  EasyExcel.read(excelDo.getImportFile(),T, listener).sheet().doRead();
     **/
    public static <T> List<T> readExcel(ReadExcelDTO readExcelDTO){
        ReadExcelListener<T> listener = new ReadExcelListener<>();
        EasyExcel.read(readExcelDTO.getInputStream(), readExcelDTO.getClassT(), listener).sheet().doRead();
        return listener.getDataList();
    }
    /**
     * @Description: 读取excel所有sheet页，且所有sheet页面的数据类为同一个
     * @Date: 2023/2/2
     * @Param excelDo，其中需要包括重要信息，如输入地址、文件或者流:
     * @Param Class T，泛型类，需要获取的数据的类型
     **/
    public static <T> List<T> readExcelAll(ReadExcelDTO readExcelDTO){
        ReadExcelListener<T> listener = new ReadExcelListener<>();
        EasyExcel.read(readExcelDTO.getInputStream(), readExcelDTO.getClassT(), listener).doReadAll();
        return listener.getDataList();
    }

    /**
     * @Description: 读取excel指定的sheet页，且不同sheet页面的数据类为不同
     * @Date: 2023/2/3
     * @Param excelDo:依赖List<SheetsAndClass>参数来创建不同的监听器listener和ReadSheet，用以读取不同sheet页的数据并转为datalist存在监听器中
     **/
    public static <T> Map<String,List<T>> readExcelWithDiffentSheets(ReadExcelDTO readExcelDTO){
        Map<String, List<T>> resultMap = new HashMap<>();
        ExcelReader excelReader = EasyExcel.read(readExcelDTO.getInputStream()).build();
        ArrayList<ReadSheet> readSheets = new ArrayList<>();
        ArrayList<ReadExcelListener> readExcelListenerList = new ArrayList<>();
        //获取excelDo中的sheets和class的组合，单独创建监听器listener和sheet对象
        //这里不明白为什么不能获取正常的对象，只能获取Object对象
        for (Object object: readExcelDTO.getSheetsAndClassList()){
            SheetsAndClass sheetsAndClass = (SheetsAndClass) object;
            ReadExcelListener<Object> excelListener = new ReadExcelListener<>();
            ReadSheet readSheet = EasyExcel.readSheet(sheetsAndClass.getSheets()).head(sheetsAndClass.getClassT()).registerReadListener(excelListener).build();
            readSheets.add(readSheet);
            readExcelListenerList.add(excelListener);
        }
        excelReader.read(readSheets);
        //组装返回数据
        for (int i =0;i< readExcelListenerList.size();i++){
            resultMap.put(readSheets.get(i).getSheetName(),readExcelListenerList.get(i).getDataList());
        }
        excelReader.finish();
        return resultMap;
    }
}
