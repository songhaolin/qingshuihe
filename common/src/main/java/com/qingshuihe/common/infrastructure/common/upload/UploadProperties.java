package com.qingshuihe.common.infrastructure.common.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:设置通用文件配置，获取application中的参数
 * 这里要获取上传文件的路径
 * @Author: shl
 * @Date: 2022/9/15
 **/
@Component
@ConfigurationProperties(prefix = "file.upload")
@Data
public class UploadProperties {

    private String hostPath;
}
