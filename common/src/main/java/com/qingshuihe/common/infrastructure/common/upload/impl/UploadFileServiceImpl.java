package com.qingshuihe.common.infrastructure.common.upload.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingshuihe.common.domain.attach.AttachService;
import com.qingshuihe.common.domain.attach.entity.AttachEntity;
import com.qingshuihe.common.infrastructure.common.upload.UploadFileService;
import com.qingshuihe.common.infrastructure.common.upload.UploadProperties;
import com.qingshuihe.common.interfaces.outbond.common.FileVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.FileUtils;
import com.qingshuihe.common.utils.WebResponseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/15
 **/
@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private AttachService attachService;

    @Autowired
    private UploadProperties uploadProperties;

    @Override
    public ResultDo<List<FileVo>> uploadFiles(MultipartFile[] files) {
        ArrayList<FileVo> fileVos = new ArrayList<>();
        //判断前端是否有传来文件
        if (null==files||files.length==0){
            return WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR,"不存在需要上传的文件",null);
        }else {
            //设置返回的list信息
            ArrayList<AttachEntity> attachEntities = new ArrayList<>();
            /**保存需要上传的文件，这里需要分两步处理，
             * 第一步根据文件信息构建文件实例加入list中，用以后续存储到数据库中;
             * 第二步，将文件真实落地到文件磁盘上*/
            for (MultipartFile file :files){
                FileVo fileVo = new FileVo();
                AttachEntity attachEntity = this.saveFiles(file);
                attachEntities.add(attachEntity);
                BeanUtils.copyProperties(attachEntity,fileVo);
                fileVos.add(fileVo);
            }
            attachService.saveBatch(attachEntities);
        }
        return WebResponseUtils.setSuccessResultDo(fileVos);
    }

    @Override
    public void downloadFiles(HttpServletResponse response, List<String> fileIds) throws IOException {
        //清空返回的内容
        response.reset();
        //根据fileIds从数据库中获取需要下载的文件的信息
        LambdaQueryWrapper<AttachEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(AttachEntity::getFileId,fileIds);
        List<AttachEntity> attachEntities = attachService.list(lambdaQueryWrapper);
        if (!attachEntities.isEmpty()){
            if (attachEntities.size()>1){
                this.multipartFilesDownload(attachEntities,response);
            }else {
                this.singleFileDownload(attachEntities.get(0),response);
            }
        }
    }

    private AttachEntity saveFiles(MultipartFile file){
        AttachEntity attachEntity = new AttachEntity();
        //获取当前文件的唯一uuid
        String fileId = UUID.randomUUID().toString().replace("-", "");
        attachEntity.setFileId(fileId);
        //获取文件的真实名称
        String originalFilename = file.getOriginalFilename();
        attachEntity.setName(originalFilename);
        //设置文件类型
        attachEntity.setFileType(file.getContentType());
        //设置文件大小
        attachEntity.setFileSize(FileUtils.getFileSize(file.getSize()));
        //获取文件存储路径，存储在数据库中的路径为当filePath=年/月/日/fileId.fileType
//        真实落地路径为:hostPath+filePath,fileId作为文件落地名称是为了保证文件安全性，hostPath不落地数据库也是为了提高安全性
        //在真实路径下创建年月日目录
        File floder = FileUtils.mkdir(uploadProperties.getHostPath() + FileUtils.getCurrentDatePath());
        //将隐去了hostPath的filePath存入数据库中
        String newName = fileId+originalFilename.substring(originalFilename.lastIndexOf("."));
        attachEntity.setFilePath(FileUtils.getCurrentDatePath()+"\\"+newName);
        //根据上面创建的目录和计划落地的文件名生成新的文件，将传入的文件file中的内容导入创建的空白文件中
        try{
            file.transferTo(new File(floder,newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attachEntity;
    }
    /**
     * @Description: 单个文件下载
     * @Date: 2022/9/16
     * @Param attachEntity:
     * @Param response:
     **/
    void singleFileDownload(AttachEntity attachEntity,HttpServletResponse response) throws IOException {
        downFile(response,attachEntity.getName(),uploadProperties.getHostPath()+attachEntity.getFilePath());
    }
    /**
     * @Description: 多文件以压缩包形式下载
     * @Date: 2022/9/16
     * @Param attachEntities:
     * @Param response:
     **/
    void multipartFilesDownload(List<AttachEntity> attachEntities,HttpServletResponse response) {
        //声明对文件下载的压缩包的名称
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //设置压缩包名称
        String zipFileName = simpleDateFormat.format(new Date()) + ".zip";
        //设置压缩包存储的临时路径
        String strZipPathTemp = uploadProperties.getHostPath() + "\\" + zipFileName;
        //声明压缩文件输出流
        ZipOutputStream zipOutputStream = null;
        //声明文件输入流
        FileInputStream zipSource = null;
        //声明输入缓冲流
        BufferedInputStream bufferedInputStream = null;
        //创建压缩文件
        File zipFile = new File(strZipPathTemp);
        try {
            //构建最终压缩包的输入流,使用创建的压缩文件构造文件输出流，继而构建压缩文件输出流
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            //遍历需要下载的文件列表，依次加入压缩文件包中
            for (AttachEntity attachEntity : attachEntities) {
                //获取文件名并对文件名进行编码，防止中文格式乱码
                String realFileName = URLDecoder.decode(attachEntity.getName(), "utf-8");
                //获取文件的真实存储路径，同样需要对获取到的路径进行编码，防止中文乱码
                String realFilePath = URLDecoder.decode(uploadProperties.getHostPath() + attachEntity.getFilePath(), "utf-8");
                //读取当前文件
                File file = new File(realFilePath);
                //若当前文件存在，则加入压缩输出文件流中
                if (file.exists()) {
                    //将需要压缩的文件格式化为输入流
                    zipSource = new FileInputStream(file);
                    //在压缩目录文件中设置压缩文件的名字
                    ZipEntry zipEntry = new ZipEntry(realFileName);
                    //定位该压缩条目位置，开始写入文件到压缩包中
                    zipOutputStream.putNextEntry(zipEntry);
                    bufferedInputStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read = 0;
                    byte[] buff = new byte[1024 * 10];
                    while ((read = bufferedInputStream.read(buff, 0, 1024 * 10)) != -1) {
                        zipOutputStream.write(buff, 0, read);
                    }

                }

            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != bufferedInputStream) {
                    bufferedInputStream.close();
                }
                if (null != zipOutputStream) {
                    zipOutputStream.flush();
                    zipOutputStream.close();
                }
                if (null != zipSource) {
                    zipSource.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            //判断生成压缩包成功，则下载压缩包，后删除临时文件
            if (zipFile.exists()) {
                downFile(response,zipFileName,strZipPathTemp);
                zipFile.delete();
            }
        }
    }
    /**
     * @Description: 根据文件名和文件路径下载文件
     * @Date: 2022/9/19
     * @Param response:
     * @Param filename:
     * @Param path:
     **/
    private void downFile(HttpServletResponse response,String filename,String path ) {
        if (filename != null) {
            FileInputStream is = null;
            BufferedInputStream bs = null;
            OutputStream os = null;
            try {
                File file = new File(path);
                if (file.exists()) {
                    //设置Headers
                    response.setHeader("Content-Type", "application/octet-stream");
                    //设置下载的文件的名称-该方式已解决中文乱码问题
                    response.setCharacterEncoding("utf-8");
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename,"utf-8"));
                    response.setContentType("application/octet-stream;charset=utf-8");

                    is = new FileInputStream(file);
                    bs = new BufferedInputStream(is);
                    os = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = bs.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                } else {
                    WebResponseUtils.renderResponse(response, "资源不存在");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (bs != null) {
                        bs.close();
                    }
                    if (os != null) {
                        os.flush();
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
