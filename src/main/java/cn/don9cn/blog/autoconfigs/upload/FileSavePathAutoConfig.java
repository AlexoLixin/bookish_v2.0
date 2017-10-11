package cn.don9cn.blog.autoconfigs.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuxindong
 * @Description: 自动创建注入FileSavePathConfig
 * @Create: 2017/10/11 9:46
 * @Modify:
 */
@Configuration
public class FileSavePathAutoConfig {

    /**
     * 服务器地址
     */
    @Value("${fileSavePath.serverUrl}")
    private String serverUrl;

    /**
     * 服务器根目录
     */
    @Value("${fileSavePath.serverRoot}")
    private String serverRoot;

    /**
     * 图片保存位置
     */
    @Value("${fileSavePath.images}")
    private String images;

    /**
     * 上传文件保存位置
     */
    @Value("${fileSavePath.uploadFiles}")
    private String uploadFiles;

    @Bean
    public FileSavePathConfig fileSavePathConfig(){
        FileSavePathConfig config = new FileSavePathConfig();
        config.setServerUrl(serverUrl);
        config.setServerRoot(serverRoot);
        config.setImages(images);
        config.setUploadFiles(uploadFiles);
        return config;
    }

}
