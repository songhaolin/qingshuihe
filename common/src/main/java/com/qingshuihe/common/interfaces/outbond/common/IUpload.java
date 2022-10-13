package com.qingshuihe.common.interfaces.outbond.common;

import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "文件上传下载")
public interface IUpload {

    @ApiOperation(value = "/common/uploadFiles",notes = "文件上传")
    ResultDo<List<FileVo>> uploadFiles(MultipartFile[] files);

    @ApiOperation(value = "/common/downloadFiles",notes = "文件下载")
    void  downloadFiles(HttpServletResponse response,List<String> fileIds) throws IOException;
}
