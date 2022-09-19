package com.qingshuihe.common.infrastructure.common.upload;

import com.qingshuihe.common.interfaces.outbond.common.FileVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/15
 **/
public interface UploadFileService {

    ResultDo<List<FileVo>> uploadFiles(MultipartFile[] files);

    void downloadFiles(HttpServletResponse response, List<String> fileIds) throws IOException;
}
