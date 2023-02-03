package com.qingshuihe.common.infrastructure.easyexcel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/1/31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Demo1DTO {
    /**
     * @ExcelProperty注解释义：表示该字段需要导出,
     * 其中value和index都具有标识的作用,在写入或者读取时,都可以作为匹配属性和excel中数据的存在,即:excel中列名为序号,则该列数据无论是导入还是导出,都能匹配成功
     * value表示表头的名称，{}大括号表示表头层级，相同层级的同名表头，如果相连会自动合并,用来实现复杂表头功能
     * index表示该列的顺序，如果需要插入或者调整顺序直接调整index值即可;
     * 如果index值相同会异常；
     * 如果有的没有顺序，则会按照输出的上下层关系在excel依次补空
     * @ExcelIgnore 表示该字段不需要导出,也可通过代码增加set处理
     * @NumberFormat(value = "#.##")表示格式化该字段，保留小数点后两位,当需要对外部导入的数字做格式化时，这个注解不会生效，建议改为String类型后使用该注解
     * @DateTimeFormat(value = "yyyy年mm月dd日 HH时mi分ss秒")表示格式化该字段，设置日志导出格式，用的是easyexcel的依赖，不是spring的
     * 注意，这里的DateTimeFormat格式化在将外部excel导入系统时也可以做对应的转化，即可将外部的2023年02月02日 16时09分01秒 转为date
     **/
//    @ExcelProperty(value = {"个人属性","序号"},index = 0)
    @ExcelProperty(value = {"序号"},index = 0)
    private int aaaaa;



}
