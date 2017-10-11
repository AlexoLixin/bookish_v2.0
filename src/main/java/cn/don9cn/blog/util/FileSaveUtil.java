package cn.don9cn.blog.util;

import cn.don9cn.blog.action.system.upload.UploadAction;
import cn.don9cn.blog.autoconfigs.upload.FileSavePathConfig;
import cn.don9cn.blog.exception.ExceptionWrapper;
import cn.don9cn.blog.model.system.file.UploadFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author: liuxindong
 * @Description: 文件保存工具类
 * @Create: 2017/10/11 10:18
 * @Modify:
 */
public class FileSaveUtil {

    private static Logger logger = Logger.getLogger(UploadAction.class);

    /**
     * 文件保存目录, 以 年-月-日 的形式
     * @param dateTime
     * @return
     */
    public static String getSaveDir(LocalDateTime dateTime){
        return dateTime.getYear() + "-" + dateTime.getMonth() + "-" + dateTime.getDayOfMonth();
    }

    /**
     * 保存上传文件
     * @param file
     * @param fileSavePathConfig
     * @return
     */
    public static Optional<UploadFile> saveFile(MultipartFile file, FileSavePathConfig fileSavePathConfig){

        // 获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        // 文件的原始名称
        String originalFilename = file.getOriginalFilename();
        // 创建文件的保存名称
        String saveFileName = Md5Util.getMD5(originalFilename + dateTime);
        // 创建保存目录
        String dir = fileSavePathConfig.getServerRoot() + fileSavePathConfig.getUploadFiles() + "/" + getSaveDir(dateTime);
        File saveDir = new File(dir);
        if(!saveDir.exists()){
            saveDir.setWritable(true,false);
            saveDir.mkdirs();
        }
        // 将文件保存至服务器
        String savePath = dir + "/" + saveFileName;
        File targetFile = new File(savePath);
        targetFile.setWritable(true,false);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("FileSaveUtil.saveFile 上传文件保存至服务器硬盘失败!");
            return Optional.empty();
        }

        return Optional.of(new UploadFile(originalFilename,saveFileName,dir));

    }

    /**
     * 保存上传图片
     * @param file
     * @param fileSavePathConfig
     * @return
     */
    public static Optional<String> saveImage(MultipartFile file, FileSavePathConfig fileSavePathConfig){

        // 获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        // 图片的原始名称
        String originalFilename = file.getOriginalFilename();
        // 创建图片的保存名称
        String saveFileName = Md5Util.getMD5(originalFilename + dateTime) + "." + originalFilename.split("\\.")[1];
        // 创建保存目录
        String dir = getSaveDir(dateTime);
        String dirPath = fileSavePathConfig.getServerRoot() + fileSavePathConfig.getImages() + "/" + dir;
        File saveDir = new File(dirPath);
        if(!saveDir.exists()){
            saveDir.setWritable(true,false);
            saveDir.mkdirs();
        }
        // 将文件保存至服务器
        String savePath = dirPath + "/" + saveFileName;
        File targetFile = new File(savePath);
        targetFile.setWritable(true,false);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("FileSaveUtil.saveFile 上传图片保存至服务器硬盘失败!");
            return Optional.empty();
        }

        return Optional.of(fileSavePathConfig.getServerUrl() + fileSavePathConfig.getImages() + "/" + dir + "/" + saveFileName);

    }

}
