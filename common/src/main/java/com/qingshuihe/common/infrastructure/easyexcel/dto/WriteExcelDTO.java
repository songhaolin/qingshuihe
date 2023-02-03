package com.qingshuihe.common.infrastructure.easyexcel.dto;

import com.qingshuihe.common.utils.StringUtils;
import lombok.Data;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Set;

/**
 * @Description:导出excel基本对象
 * @Author: shl
 * @Date: 2023/1/31
 **/
@Data
public class WriteExcelDTO<T> extends BaseExcelDTO {


    /**
     * @Description: 导出excel路径
     * @Date: 2023/2/1
     **/
    private String exportPath;
    /**
     * @Description: 导出数据
     * @Date: 2023/2/1
     **/
    private List<T> dataList;

    /**
     * @Description: 导出sheet名称--单个sheet
     * @Date: 2023/2/1
     **/
    private String sheet;
    /**
     * @Description: 多个sheet
     * @Date: 2023/2/2
     **/
    private String[] sheets;
    /**
     * @Description: 需要排除的字段；也可通过直接在数据类加注解@ExcelIgnore实现
     * @Date: 2023/2/1
     **/
    private Set<String> excludeColumns;
    /**
     * @Description: 只需要导出的字段
     * @Date: 2023/2/1
     **/
    private Set<String> includeColumns;

    /**
     * @Description: 不允许直接new一个空对象
     * @Date: 2023/2/3
     * @Param null:
     **/
    private WriteExcelDTO() {
    }

    /**
     * @Description: 构造方法，内聚excel的导出地址和导出数据并校验
     * @Date: 2023/2/2
     * @Param String exportPath 导出文件路径
     * @Param List<T> dataList 导出数据
     **/
    public WriteExcelDTO(String exportPath, List<T> dataList) throws ValidationException {
        if (StringUtils.isEmpty(exportPath)||(!exportPath.contains(".xlsx ") && !exportPath.contains(".xls"))) {
            throw new ValidationException("非法的excel导出路径");
        }
        this.exportPath = exportPath;
        if (dataList == null || dataList.isEmpty()) {
            throw new ValidationException("导出数据不能为空");
        }
        this.dataList = dataList;
    }
}
