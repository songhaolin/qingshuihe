package com.qingshuihe.common.infrastructure.easyexcel.dto;

import lombok.Data;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * @Description:导出excel基本对象
 * @Author: shl
 * @Date: 2023/1/31
 **/
@Data
public class ReadExcelDTO<T> extends BaseExcelDTO {

    /**
     * @Description: 导入文件路径
     * @Date: 2023/2/2
     **/
    private String importPath;

    /**
     * @Description: 导入文件
     * @Date: 2023/2/2
     **/
    private File importFile;

    /**
     * @Description: 导入文件流
     * @Date: 2023/2/2
     **/
    private InputStream inputStream;


    /**
     * @Description: 导入excel对应的Class
     * @Date: 2023/2/2
     **/
    private Class classT;

    /**
     * @Description: sheets和Class的组合
     * 背景:导入的excel包含多个不同类的sheets，包含多个Class；
     * @Date: 2023/2/2
     **/
    private List<SheetsAndClass> sheetsAndClassList;


    /**
     * @Description: 不允许直接new一个空对象
     * @Date: 2023/2/3
     * @Param null:
     **/
    private ReadExcelDTO() {
    }

    /**
     * @Description: 构造方法，内聚excel的导入地址并校验；最后转为inputStream输入流，方便后续读取
     * @Date: 2023/2/2
     * @Param importPath: 导入文件路径
     **/
    public ReadExcelDTO(String importPath) throws ValidationException, FileNotFoundException {
        if (!importPath.contains(".xlsx ") && !importPath.contains(".xls")) {
            throw new ValidationException("非法的excel路径");
        }
        this.importPath = importPath;
        this.inputStream = new FileInputStream(new File(importPath));
    }

    /**
     * @Description: 构造方法，内聚excel的导入文件，并判断是否为正确文件;最后转为inputStream输入流，方便后续读取
     * @Date: 2023/2/2
     * @Param importFile:导入文件
     **/
    public ReadExcelDTO(File importFile) throws ValidationException, FileNotFoundException {
        if (!importPath.contains(".xlsx ") && !importPath.contains(".xls")) {
            throw new ValidationException("非法的excel路径");
        }
        this.importFile = importFile;
        this.inputStream = new FileInputStream(importFile);
    }

    /**
     * @Description: 构造方法，内聚excel的导入文件，并判断是否为正确文件
     * @Date: 2023/2/2
     * @Param inputStream: 导入文件流
     **/
    public ReadExcelDTO(InputStream inputStream) throws ValidationException {
        if (inputStream == null) {
            throw new ValidationException("导入文件为空！");
        }
        this.inputStream = inputStream;
    }

    public void setClassT(Class classT) throws ValidationException {
        if (classT == null) {
            throw new ValidationException("导入数据类不可为空");
        }
        this.classT = classT;
    }

    public void setSheetsAndClassList(List<SheetsAndClass> sheetsAndClassList) throws ValidationException {
        if (sheetsAndClassList == null || sheetsAndClassList.isEmpty()) {
            throw new ValidationException("导入数据sheet与class组合不可为空");
        }
        this.sheetsAndClassList = sheetsAndClassList;
    }
}
