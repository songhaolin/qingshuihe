package com.qingshuihe.common.infrastructure.easyexcel.dto;

import com.qingshuihe.common.utils.StringUtils;
import lombok.Data;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/2/3
 **/
@Data
public class FillExcelDTO<T> extends BaseExcelDTO{

    /**
     * @Description: 模板所处路径
     * @Date: 2023/2/3
     **/
    private String templatePath;
    /**
     * @Description: 导出文件存储路径
     * @Date: 2023/2/3
     **/
    private String exportPath;
    /**
     * @Description: 填充数据对象，字段需与模板中的字段匹配，该T需要对照模板新增
     * @Date: 2023/2/3
     **/
    private List<T> fillObjectList;

    private FillExcelDTO(){}

    public FillExcelDTO(String templatePath, String exportPath) throws ValidationException {
        if (StringUtils.isEmpty(templatePath)||(!templatePath.contains(".xlsx ") && !templatePath.contains(".xls"))) {
            throw new ValidationException("非法模板文件路径");
        }
        if (StringUtils.isEmpty(exportPath)||(!exportPath.contains(".xlsx ") && !exportPath.contains(".xls"))) {
            throw new ValidationException("非法导出excel路径");
        }
        this.templatePath =templatePath;
        this.exportPath = exportPath;
    }
}
