package com.qingshuihe.common.application.upload;

import com.qingshuihe.common.infrastructure.common.upload.UploadFileService;
import com.qingshuihe.common.interfaces.outbond.common.FileVo;
import com.qingshuihe.common.interfaces.outbond.common.IUpload;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/15
 **/
@RestController
@RequestMapping("/common")
@ApiModel(description = "文件上传下载")
public class UploadController implements IUpload {
    @Autowired
    private UploadFileService uploadFileService;


    //在post请求中设置文件头的格式内容格式，表明前端传来的参数为多文件
    @PostMapping(value = "/uploadFiles",headers = {"content-type=multipart/form-data"})
    @PreAuthorize("hasAnyAuthority('/common/uploadFiles')||hasRole('admin')")
    @Override
    public ResultDo<List<FileVo>> uploadFiles(@RequestPart("files") MultipartFile[] files) {
        return uploadFileService.uploadFiles(files);
    }
    @PostMapping(value = "/downloadFiles")
    @PreAuthorize("hasAnyAuthority('/common/downloadFiles')||hasRole('admin')")
    @Override
    public void downloadFiles(HttpServletResponse response,@RequestBody List<String> fileIds) throws IOException {
        uploadFileService.downloadFiles(response,fileIds);
    }


}
