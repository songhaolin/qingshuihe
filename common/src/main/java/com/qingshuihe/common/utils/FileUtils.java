package com.qingshuihe.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/15
 **/
public class FileUtils {


    /**
     * 按照时间创建文件夹：年/月/日 ---》 2022/8/21
     * @param  dir 传过来的绝对路径，例如 D:\\qishuihe\\uploadFile
     * @return
     */
    public static File mkdir(String dir){
        File folder = new File(dir);
        //判断目录是否存在，不存在则创建目录
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    public static String getCurrentDatePath(){
        //获取当前日期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //获取年分 例如:2022
        String Y = "\\"+String.valueOf(calendar.get(Calendar.YEAR));
        //获取月分 例如:8
        String M = "\\"+String.valueOf(calendar.get(Calendar.MONTH)+1);
        //获取日期 例如:21
        String D = "\\"+String.valueOf(calendar.get(Calendar.DATE));
        return Y+M+D;
    }
    /**
     * 文件大小单位转换
     * @param size
     * @return
     */
    public static String getFileSize(Long size){
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return size + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return Math.round(size * 100) / 100 + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            return Math.round(size * 100) / 100 + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            return Math.round(size / 1024 * 100) / 100 + "GB";
        }

    }
}
