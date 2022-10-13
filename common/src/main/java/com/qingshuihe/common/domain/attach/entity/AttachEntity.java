package com.qingshuihe.common.domain.attach.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingshuihe.common.base.BaseEntity;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/15
 **/
@Data
@TableName("sys_attach_t")
public class AttachEntity extends BaseEntity {

    /**
     * @Description: 附件ID，用来展示给前端的uuid，作为下载使用的唯一id，并作为文件存在仓库的唯一名称
     * @Date: 2022/9/15
     **/
    private String fileId;
    /**
     * @Description: 附件存储路径，不包含绝对路径的文件所属目录，真实上传下载需要配合配置的路径使用
     * @Date: 2022/9/15
     **/
    private String filePath;
    /**
     * @Description: 附件真实名称
     * @Date: 2022/9/15
     **/
    private String name;
    /**
     * @Description: 附件大小
     * @Date: 2022/9/15
     **/
    private String fileSize;
    /**
     * @Description: 附件类型
     * @Date: 2022/9/15
     **/
    private String fileType;
    /**
     * @Description: 附件描述
     * @Date: 2022/9/15
     **/
    private String description;
}
